package com.shkbostan.ailoganalyzer.parser;

import com.shkbostan.ailoganalyzer.model.LogEntry;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



/**
 * Created on Aug, 2025
 *
 * Parses raw log lines into structured LogEntry objects.
 * Provides detailed logging for parsing errors and processing status.
 *
 * Author: s Bostan
 */
@Slf4j
public class LogParser {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Parses raw log lines into a list of LogEntry objects.
     *
     * @param rawLogs List of log lines
     * @return List of parsed LogEntry objects
     */
    public List<LogEntry> parse(List<String> rawLogs) {
        log.info("Starting parsing of {} log lines", rawLogs.size());
        List<LogEntry> entries = new ArrayList<>();

        for (String line : rawLogs) {
            // [2025-08-30 09:12:55] ERROR payment-service - NullPointerException
            try {
                String timestampStr = line.substring(1, 20); // 2025-08-30 09:12:55
                LocalDateTime timestamp = LocalDateTime.parse(timestampStr, FORMATTER);


                String rest = line.substring(22);
                String[] parts = rest.split(" ", 3); // level, service, message

                String level = parts[0];
                String service = parts[1];
                String message = parts.length > 2 ? parts[2] : "";

                entries.add(new LogEntry(timestamp, level, service, message));
                log.debug("Parsed log line successfully: [{}] {} {} {}", timestamp, level, service, message);

            } catch (Exception e) {
                log.warn("Could not parse log line: {}", line, e);
            }
        }

        log.info("Parsing completed. Successfully parsed {} entries out of {}", entries.size(), rawLogs.size());
        return entries;
    }
}