package com.shkbostan.ailoganalyzer.controller;

import com.shkbostan.ailoganalyzer.service.LogService;
import org.springframework.web.bind.annotation.*;

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
        logService.processLogFile(path);
        return "✅ Log file processed: " + path;
    }

    // get error report
    @GetMapping("/report")
    public Map<String, Integer> getReport() {
        return logService.getErrorFrequency();
    }
}
