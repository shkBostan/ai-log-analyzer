# AI Log Analyzer

**AI Log Analyzer** is a Spring Boot application for parsing, analyzing, and summarizing log files.  
It provides:
- **Error frequency reports** from logs
- **REST APIs** for uploading and analyzing log files
- **AI-powered log summarization** using OpenAI GPT models
- **Detailed structured logging** for traceability and debugging

---

## Features

- 📂 **Log Ingestion**: Read raw logs from file system
- 🔎 **Log Parsing**: Convert log lines into structured `LogEntry` objects
- 📈 **Log Analysis**: Detect error frequencies and generate reports
- 🧾 **Reporting**: Console & API-based error frequency reporting
- 🤖 **AI Summarization**: Summarize logs with OpenAI GPT models
- 🌐 **REST API**: Upload log files, fetch reports, and get summaries

---

## Tech Stack

- **Java 17+**
- **Spring Boot 3**
- **Lombok**
- **OkHttp & Jackson** (for AI integration)
- **OpenAI GPT API** (for AI-based log summarization)

---

## Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/ai-log-analyzer.git
   cd ai-log-analyzer
   ```
   
---
## License 
This project is licensed under the Apache License 2.0 – see the [LICENSE](LICENSE.txt) file for details.


