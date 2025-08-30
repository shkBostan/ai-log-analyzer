package com.shkbostan.ailoganalyzer.parser;

import com.shkbostan.ailoganalyzer.model.LogEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */

public class LogParser {

    public List<LogEntry> parse(List<String> rawLogs) {
        List<LogEntry> entries = new ArrayList<>();

        for (String line : rawLogs) {
            // [2025-08-30 09:12:55] ERROR payment-service - NullPointerException
            try {
                String[] parts = line.split(" ", 5);
                LocalDateTime timestamp = LocalDateTime.parse(parts[0].replace("[","") + "T" + parts[1]);
                String level = parts[2];
                String service = parts[3];
                String message = parts.length > 4 ? parts[4] : "";

                entries.add(new LogEntry(timestamp, level, service, message));
            } catch (Exception e) {
                System.err.println("Could not parse log line: " + line);
            }
        }
        return entries;
    }
}
