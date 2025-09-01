package com.shkbostan.ailoganalyzer.controller;

import com.shkbostan.ailoganalyzer.model.LogEntry;
import com.shkbostan.ailoganalyzer.parser.LogParser;
import com.shkbostan.ailoganalyzer.service.AISummarizerService;
import com.shkbostan.ailoganalyzer.service.LocalSummarizerService;
import com.shkbostan.ailoganalyzer.ingestion.LogReader;
import com.shkbostan.ailoganalyzer.analyzer.LogAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


/**
 * Unified REST controller for log analysis and summarization.
 * <p>
 * This controller provides a single endpoint that:
 * <ul>
 *     <li>Reads the log file</li>
 *     <li>Parses and analyzes errors</li>
 *     <li>Generates an AI-based summary</li>
 *     <li>Returns a unified JSON response</li>
 * </ul>
 *
 * Created on Sep, 2025
 *
 * @author
 *     s Bostan
 */
@Slf4j
@RestController
@RequestMapping("/api/logs")
public class UnifiedLogController {

    private final AISummarizerService aiSummarizerService;
    private final LocalSummarizerService localSummarizerService;
    private final LogParser parser = new LogParser();

    public UnifiedLogController(AISummarizerService aiSummarizerService,
                                LocalSummarizerService localSummarizerService) {

        this.aiSummarizerService = aiSummarizerService;
        this.localSummarizerService = localSummarizerService;
    }

    @PostMapping("/analyze")
    public Map<String, Object> analyzeLogFile(@RequestParam String path) {
        log.info("Received request to analyze log file: {}", path);

        Map<String, Object> response = new HashMap<>();
        try {
            Path logPath = Paths.get("Logs").resolve(path).toAbsolutePath();
            log.debug("Resolving path for log file: {}", path);

            if (!Files.exists(logPath)) {
                response.put("error", "❌ File not found: " + logPath);
                log.warn("Log file not found: {}", path);
                return response;
            }

            // Step 1: Read logs
            log.info("Reading and parsing logs from file: {}", path);
            LogReader reader = new LogReader(logPath.toString());
            List<String> rawLogs = reader.read();
            response.put("filePath", logPath.toString());
            response.put("logCount", rawLogs.size());

            // Step 2: Parse & Analyze
            List<LogEntry> parsedLogs = parser.parse(rawLogs);
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.analyze(parsedLogs);
            response.put("errorReport", analyzer.getErrorFrequency());

            // Step 3: Summarize with AI and Local, keep both in response
            String localSummary = localSummarizerService.summarizeLocally(rawLogs);
            response.put("localSummary", localSummary);

            try {
                log.debug("Calling AI summarizer...");
                String aiSummary = aiSummarizerService.summarizeWithAI(String.join("\n", rawLogs));
                response.put("aiSummary", aiSummary);
            } catch (Exception e) {
                // AI failed, keep aiSummary null or error message
                response.put("aiError", e.getMessage());
                log.error("AI summarization failed", e);
            }

        } catch (Exception e) {
            log.error("Unexpected error during log analysis", e);
            response.put("error", "❌ Error processing log file: " + e.getMessage());
        }
        log.info("Returning response with {} entries", response.size());
        return response;
    }
}