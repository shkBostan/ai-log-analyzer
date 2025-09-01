package com.shkbostan.ailoganalyzer.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on Sep, 2025
 *
 * @author s Bostan
 */
@Service
public class LocalSummarizerService {

    public String summarizeLocally(List<String> logs) {
        if (logs.isEmpty()) return "No logs available.";

        // Get first 3 lines
        String preview = logs.stream()
                .limit(3)
                .collect(Collectors.joining("\n"));

        // Count errors
        long errorCount = logs.stream()
                .filter(line -> line.toUpperCase().contains("ERROR"))
                .count();

        return "Local Summary:\n" +
                "Preview:\n" + preview + "\n\n" +
                "Total ERROR lines: " + errorCount;
    }
}