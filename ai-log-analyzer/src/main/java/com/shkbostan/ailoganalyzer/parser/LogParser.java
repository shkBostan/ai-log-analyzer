package com.shkbostan.ailoganalyzer.parser;

import com.shkbostan.ailoganalyzer.model.LogEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */

public class LogParser {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<LogEntry> parse(List<String> rawLogs) {
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
            } catch (Exception e) {
                System.err.println("Could not parse log line: " + line);
            }
        }
        return entries;
    }
}