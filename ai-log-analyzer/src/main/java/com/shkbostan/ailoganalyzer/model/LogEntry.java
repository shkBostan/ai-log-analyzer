package com.shkbostan.ailoganalyzer.model;

import java.time.LocalDateTime;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */

public class LogEntry {
    private LocalDateTime timestamp;
    private String level;    // INFO, ERROR, WARN
    private String service;  // مثلا payment-service
    private String message;

    public LogEntry(LocalDateTime timestamp, String level, String service, String message) {
        this.timestamp = timestamp;
        this.level = level;
        this.service = service;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getLevel() { return level; }
    public String getService() { return service; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + level + " (" + service + ") -> " + message;
    }
}