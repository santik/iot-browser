package com.relay42.browser.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.relay42.generated.ReadingRequest;
import com.relay42.generated.ReadingResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ReadingsRequestClient {

    private static final String API_ENDPOINT = "http://localhost:8080/readings/";

    public ReadingResponse sendReadingRequest(ReadingRequest readingRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_ENDPOINT))
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(readingRequest)))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), ReadingResponse.class);
    }
}
