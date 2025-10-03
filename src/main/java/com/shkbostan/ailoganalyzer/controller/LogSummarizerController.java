package com.shkbostan.ailoganalyzer.controller;

import com.shkbostan.ailoganalyzer.service.AISummarizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */
@RestController
@RequestMapping("/api/logs")
public class LogSummarizerController {
    private final AISummarizerService aiSummarizerService;

    @Autowired
    public LogSummarizerController(AISummarizerService aiSummarizerService) {
        this.aiSummarizerService = aiSummarizerService;
    }

    @PostMapping("/summarize")
    public Map<String, Object> summarizeLogs(@RequestBody List<String> logs) {
        // get log
        StringBuilder allLogs = new StringBuilder();
        logs.forEach(line -> allLogs.append(line).append("\n"));

        // summarizing
        // replace with ai next
        String summary = simpleSummarizer(allLogs.toString());

        // output
        Map<String, Object> response = new HashMap<>();
        response.put("originalCount", logs.size());
        response.put("summary", summary);

        return response;
    }

    private String simpleSummarizer(String text) {
        if (text.length() < 200) return text;
        return "Summary: " + text.substring(0, 200) + "... [truncated]";
    }

    @PostMapping("/summarize/ai")
    public Map<String, Object> summarizeWithAI(@RequestBody List<String> logs) {
        Map<String, Object> response = new HashMap<>();
        try {
            String aiSummary = aiSummarizerService.summarizeWithAI(String.join("\n", logs));
            response.put("summary", aiSummary);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

}
