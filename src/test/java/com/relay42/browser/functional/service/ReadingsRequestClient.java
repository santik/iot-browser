package com.relay42.browser.functional.service;

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

    public double getAverageByDeviceId(String deviceId) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = ReadingRequestService.getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.AVERAGE);
        ReadingResponse readingResponse = sendReadingRequest(readingsRequest);
        return readingResponse.getValue();
    }

    public double getMaxByDeviceId(String deviceId) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = ReadingRequestService.getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.MAX);
        ReadingResponse readingResponse = sendReadingRequest(readingsRequest);
        return readingResponse.getValue();
    }

    public double getMinByDeviceId(String deviceId) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = ReadingRequestService.getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.MIN);
        ReadingResponse readingResponse = sendReadingRequest(readingsRequest);
        return readingResponse.getValue();
    }

    public double getAverageByGroupId(String groupId) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = ReadingRequestService.getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.AVERAGE);
        ReadingResponse readingResponse = sendReadingRequest(readingsRequest);
        return readingResponse.getValue();
    }

    public double getMaxByGroupId(String groupId) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = ReadingRequestService.getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.MAX);
        ReadingResponse readingResponse = sendReadingRequest(readingsRequest);
        return readingResponse.getValue();
    }

    public double getMinByGroupId(String groupId) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = ReadingRequestService.getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.MIN);
        ReadingResponse readingResponse = sendReadingRequest(readingsRequest);
        return readingResponse.getValue();
    }
}
