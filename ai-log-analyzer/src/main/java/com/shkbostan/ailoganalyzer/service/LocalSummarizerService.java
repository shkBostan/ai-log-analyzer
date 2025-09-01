package com.shkbostan.ailoganalyzer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on Sep, 2025
 *
 * Service to create a simple local summary of logs as a fallback when AI summarization fails.
 * Provides a preview of first few log lines and counts total ERROR lines.
 *
 * @author s Bostan
 */
@Slf4j
@Service
public class LocalSummarizerService {


    /**
     * Summarizes the given logs locally.
     *
     * @param logs List of raw log lines
     * @return A summary string including preview and total ERROR count
     */
    public String summarizeLocally(List<String> logs) {
        log.info("Starting local summarization for {} log lines", logs.size());

        if (logs.isEmpty()) {
            log.warn("No logs provided for summarization");
            return "No logs available.";
        }

        // Get first 3 lines
        String preview = logs.stream()
                .limit(3)
                .collect(Collectors.joining("\n"));
        log.debug("Preview of first 3 lines:\n{}", preview);

        // Count errors
        long errorCount = logs.stream()
                .filter(line -> line.toUpperCase().contains("ERROR"))
                .count();
        log.info("Total ERROR lines counted: {}", errorCount);

        String summary = "Local Summary:\n" +
                "Preview:\n" + preview + "\n\n" +
                "Total ERROR lines: " + errorCount;

        log.debug("Local summary generated successfully");
        return summary;
    }
}