# AI Log Analyzer

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![License](https://img.shields.io/badge/license-MIT-blue)]()
[![Java](https://img.shields.io/badge/Java-17-orange)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)]()

AI Log Analyzer is a professional-grade log analysis microservice built with Spring Boot.  
It parses, analyzes, and summarizes system logs, leveraging AI (OpenAI GPT) with a fallback local summarizer for robust operations.

## Key Features

- **Log Ingestion & Parsing**
    - Reads log files from configurable paths.
    - Parses timestamp, level, service, and message.
    - Handles malformed lines gracefully with logs.

- **Error Analysis**
    - Counts error occurrences per message.
    - Provides detailed reports for troubleshooting.

- **AI Summarization**
    - Summarizes logs using OpenAI GPT models.
    - Fallback to a local summarizer if AI fails or quota exceeded.

- **Professional Logging**
    - Structured logging with `@Slf4j`.
    - INFO logs for high-level steps, DEBUG for detailed analysis, ERROR for exceptions.

- **REST API Endpoints**
    - `/api/logs/analyze?path=...` → Full analysis & AI summary.

## Architecture Overview

- **Controllers** – Handle HTTP requests and return JSON.
- **Services** – Core logic for log reading, parsing, analyzing, and summarizing.
- **Models** – `LogEntry` represents individual log lines.
- **Analyzer** – `LogAnalyzer` computes error frequencies.
- **Summarizers** – `AISummarizerService` (AI), `LocalSummarizerService` (fallback).
- **Logging** – Centralized, structured logging with Lombok `@Slf4j`.

## Getting Started

### Prerequisites
- Java 17+
- Maven
- OpenAI API key (optional if you want AI summaries)

### Build and Run

```bash
git clone https://github.com/shkBostan/ai-log-analyzer.git
cd ai-log-analyzer
mvn clean install
mvn spring-boot:run
```


### Example Request
```code
curl -X POST 'http://localhost:9090/api/logs/analyze?path=app.log'
```
### Example Response
```json
{
  "filePath": "F:\\ai-log-analyzer\\Logs\\app.log",
  "logCount": 3,
  "errorReport": {
    "- TimeoutException": 1,
    "- NullPointerException": 1
  },
  "summary": "AI Summary: ..."
}
```

## Author

s Bostan

## Created

Aug, 2025


## License

MIT License © 2025 S. Bostan  
Free to use, modify, and distribute, **as long as the original author’s name is preserved**. No warranty is provided.
