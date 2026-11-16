package dev.htfci.kidaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


@Service
public class AiServiceImpl implements AiService {
    private final FileStorageProperties properties;
    private final ObjectMapper objectMapper;


    public AiServiceImpl(FileStorageProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }


    @Override
    public HttpResponse<String> send(TaskDto taskDto) {
        URI uri = properties.aiServiceUri().resolve("/summarize");

        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(taskDto);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Failed to serialize task dto to JSON", ex);
        }

        var httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try (var client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()) {
            return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ex) {
            throw new UncheckedIOException("Network or I/O error occurred: " + ex.getMessage(), ex);
        } catch (InterruptedException iex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("AI service request was interrupted", iex);
        }
    }
}
