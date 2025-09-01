package com.shkbostan.ailoganalyzer.analyzer;

import com.shkbostan.ailoganalyzer.model.LogEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on Aug, 2025
 *
 * Analyzes logs to calculate error frequency.
 * Provides detailed logging for error counting and analysis progress.
 *
 * Author: s Bostan
 */
@Slf4j
public class LogAnalyzer {
    private final Map<String, Integer> errorFrequency = new HashMap<>();

    /**
     * Analyzes a list of LogEntry objects and counts error occurrences.
     *
     * @param logs List of parsed log entries
     */
    public void analyze(List<LogEntry> logs) {
        log.info("Starting analysis of {} log entries", logs.size());

        for (LogEntry entry : logs) {
            if ("ERROR".equalsIgnoreCase(entry.getLevel())) {
                errorFrequency.merge(entry.getMessage(), 1, Integer::sum);
                log.debug("Incremented error count for '{}', total now: {}",
                        entry.getMessage(), errorFrequency.get(entry.getMessage()));
            }
        }

        log.info("Analysis completed. Found {} distinct error messages.", errorFrequency.size());
    }

    /**
     * Returns the frequency map of errors.
     *
     * @return Map where key = error message, value = occurrence count
     */
    public Map<String, Integer> getErrorFrequency() {
        return errorFrequency;
    }
}