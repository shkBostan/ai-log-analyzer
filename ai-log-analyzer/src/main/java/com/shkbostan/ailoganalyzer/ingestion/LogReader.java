package com.shkbostan.ailoganalyzer.ingestion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */

public class LogReader {
    private final String filePath;

    public LogReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> read() {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

