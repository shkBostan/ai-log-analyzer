package com.shkbostan.ailoganalyzer.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on Aug, 2025
 * <p>
 * Service to summarize logs using OpenAI GPT model.
 * Provides AI-based summarization with structured logging for traceability.
 * Logs errors, HTTP requests/responses, and key processing steps.
 *
 * @author s Bostan
 */
@Slf4j
@Service
public class AISummarizerService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Summarizes logs using OpenAI GPT model.
     *
     * @param logs Raw logs as a string
     * @return AI summary or error message
     * @throws Exception if HTTP or JSON parsing fails
     */
    public String summarizeWithAI(String logs) throws Exception {
        log.info("Starting AI summarization for logs (length: {})", logs.length());

        String prompt = "Summarize these system logs clearly:\n" + logs;

        try {
            // --- Build JSON body with Jackson ---
            ObjectNode root = mapper.createObjectNode();
            root.put("model", "gpt-4o-mini");

            ArrayNode messages = root.putArray("messages");
            ObjectNode userMessage = messages.addObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);

            String requestBody = mapper.writeValueAsString(root);
            log.debug("Request body prepared for AI: {}", requestBody);

            // --- Build HTTP request ---
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(requestBody, JSON);

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .post(body)
                    .build();

            log.info("Sending request to OpenAI API...");

            // --- Execute request ---
            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();
                JsonNode json = mapper.readTree(responseBody);

                // Handle API error
                if (json.has("error")) {
                    String errorMsg = json.get("error").get("message").asText();
                    log.warn("AI API returned an error: {}", errorMsg);
                    return "AI Error: " + errorMsg;
                }

                // Extract AI response safely
                if (json.has("choices") &&
                        json.get("choices").isArray() &&
                        json.get("choices").size() > 0) {
                    JsonNode choice = json.get("choices").get(0);
                    if (choice.has("message") && choice.get("message").has("content")) {
                        String aiSummary = choice.get("message").get("content").asText();
                        log.info("AI summarization completed successfully");
                        return aiSummary;
                    }
                }

                log.warn("Unexpected AI response format");
                return "⚠️ Unexpected AI response format: " + responseBody;
            }
        } catch (Exception e) {
            log.error("Exception occurred during AI summarization", e);
            throw e;
        }
    }
}