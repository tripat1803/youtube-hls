package com.youtube.uploader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Builder;

@Service
public class ExternalService {

    @Autowired
    private RestTemplate restTemplate;

    private final String requestPath = "http://localhost:5001/api/docker";

    @Async
    public void triggerTranscoding(Long videoId, String rawVideoUrl) {
        TriggerTransocdingRequest request = TriggerTransocdingRequest
                .builder()
                .videoId(videoId)
                .rawVideoUrl(rawVideoUrl)
                .build();
        restTemplate.postForEntity(requestPath, request, String.class);
    }
}

@Builder
class TriggerTransocdingRequest {
    public Long videoId;
    public String rawVideoUrl;
}