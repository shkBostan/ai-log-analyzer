package com.shkbostan.ailoganalyzer.controller;

import com.shkbostan.ailoganalyzer.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    // upload file with simple path
    @PostMapping("/upload")
    public String uploadLogFile(@RequestParam String path) {
        try {
            // Construct the full path of the log file inside the "Logs" folder
            Path logPath = Paths.get("Logs").resolve(path).toAbsolutePath();

            // Check if the file exists
            if (!Files.exists(logPath)) {
                return "❌ File not found: " + logPath;
            }

            // Process the log file using logService
            logService.processLogFile(logPath.toString());

            // Return success message
            return "✅ Log file processed: " + logPath;
        } catch (Exception e) {
            // Return error message if any exception occurs
            return "❌ Error reading log file: " + e.getMessage();
        }
    }

    // get error report
    @GetMapping("/report")
    public Map<String, Integer> getReport() {
        return logService.getErrorFrequency();
    }
}
