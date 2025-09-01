package com.shkbostan.ailoganalyzer.ingestion;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Created on Aug, 2025
 *
 * Reads raw log files from the file system.
 * Provides detailed logging for file access and read errors.
 *
 * Author: s Bostan
 */
@Slf4j
public class LogReader {
    private final String filePath;

    public LogReader(String filePath) {
        this.filePath = filePath;
    }


    /**
     * Reads all lines from the log file.
     *
     * @return List of log lines, or empty list if an error occurs
     */
    public List<String> read() {
        log.info("Attempting to read log file: {}", filePath);

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            log.info("Successfully read {} lines from file: {}", lines.size(), filePath);
            return lines;
        } catch (IOException e) {
            log.error("Error reading log file {}: {}", filePath, e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}

