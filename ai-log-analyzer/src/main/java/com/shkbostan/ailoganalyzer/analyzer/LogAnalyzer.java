package com.shkbostan.ailoganalyzer.analyzer;

import com.shkbostan.ailoganalyzer.model.LogEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */

public class LogAnalyzer {
    private final Map<String, Integer> errorFrequency = new HashMap<>();

    public void analyze(List<LogEntry> logs) {
        for (LogEntry entry : logs) {
            if ("ERROR".equalsIgnoreCase(entry.getLevel())) {
                errorFrequency.merge(entry.getMessage(), 1, Integer::sum);
            }
        }
    }

    public Map<String, Integer> getErrorFrequency() {
        return errorFrequency;
    }
}
