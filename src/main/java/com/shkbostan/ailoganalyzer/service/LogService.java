package com.shkbostan.ailoganalyzer.service;

import com.shkbostan.ailoganalyzer.analyzer.LogAnalyzer;
import com.shkbostan.ailoganalyzer.ingestion.LogReader;
import com.shkbostan.ailoganalyzer.model.LogEntry;
import com.shkbostan.ailoganalyzer.parser.LogParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */

@Service
public class LogService {

    private final LogParser parser = new LogParser();
    private final LogAnalyzer analyzer = new LogAnalyzer();

    public void processLogFile(String path) {
        LogReader reader = new LogReader(path);
        List<String> rawLogs = reader.read();
        List<LogEntry> parsedLogs = parser.parse(rawLogs);
        analyzer.analyze(parsedLogs);
    }

    public Map<String, Integer> getErrorFrequency() {
        return analyzer.getErrorFrequency();
    }
}
