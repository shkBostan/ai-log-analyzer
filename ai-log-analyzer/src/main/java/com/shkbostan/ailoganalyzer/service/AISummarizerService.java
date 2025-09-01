package com.shkbostan.ailoganalyzer.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on Aug, 2025
 *
 * @author s Bostan
 */
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
        String prompt = "Summarize these system logs clearly:\n" + logs;

        // --- Build JSON body with Jackson ---
        ObjectNode root = mapper.createObjectNode();
        root.put("model", "gpt-4o-mini");

        ArrayNode messages = root.putArray("messages");
        ObjectNode userMessage = messages.addObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        String requestBody = mapper.writeValueAsString(root);

        // --- Build HTTP request ---
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(requestBody, JSON);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        // --- Execute request ---
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            JsonNode json = mapper.readTree(responseBody);

            // Handle API error
            if (json.has("error")) {
                return "AI Error: " + json.get("error").get("message").asText();
            }

            // Extract AI response safely
            if (json.has("choices") &&
                    json.get("choices").isArray() &&
                    json.get("choices").size() > 0) {
                JsonNode choice = json.get("choices").get(0);
                if (choice.has("message") && choice.get("message").has("content")) {
                    return choice.get("message").get("content").asText();
                }
            }
            return "⚠️ Unexpected AI response format: " + responseBody;
        }
    }
}