package com.shkbostan.ailoganalyzer.service;

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

    public String summarizeWithAI(String logs) throws Exception {
        String prompt = "Summarize these system logs clearly:\n" + logs;

        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        String requestBody = "{\n" +
                "  \"model\": \"gpt-4o-mini\",\n" +
                "  \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt.replace("\"", "'") + "\"}]\n" +
                "}";

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .post(RequestBody.create(requestBody, JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            JsonNode json = mapper.readTree(responseBody);
            return json.get("choices").get(0).get("message").get("content").asText();
        }
    }
}
