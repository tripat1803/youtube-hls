package com.youtube.transcoder.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.transcoder.controller.requests.CreateContainerRequest;
import com.youtube.transcoder.services.DockerService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@RestController
@RequestMapping(path = "/api/docker")
@Async
@Slf4j
public class DockerController {

    private final String imageName = "transcoder:1.0";
    private final String hostPath = "C:/Users/Lenovo/OneDrive/Desktop/LLD/youtube/youtube-services/transcoder/transcoded-videos";
    private final String containerPath = "/home/app/tmp";

    @PostMapping
    public CompletableFuture<?> createDockerContainer(@RequestBody CreateContainerRequest body) {
        DockerService dockerService = new DockerService();
        List<String> envList = new ArrayList<String>();
        envList.add(String.format("video_file_url=\"%s\"", body.rawVideoUrl));
        String videoPath = hostPath + String.format("/%s", body.videoId);
        dockerService.createAndStartContainer(imageName, envList, videoPath, containerPath);
        return CompletableFuture.completedFuture("");
    }
}