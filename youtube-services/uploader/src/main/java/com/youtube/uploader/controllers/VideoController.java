package com.youtube.uploader.controllers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.uploader.controllers.responses.GetVideosResponse;
import com.youtube.uploader.models.Video;
import com.youtube.uploader.repositories.VideoRepository;
import com.youtube.uploader.services.VideoService;
import com.youtube.uploader.services.ExternalService;
import com.youtube.uploader.services.FileUploadService;

@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@RestController
@RequestMapping(path = "/api/video")
@Async
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoService videoService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private ExternalService externalService;

    @PostMapping
    public CompletableFuture<?> assembleFinalFile(@RequestParam String filename, @RequestParam int total,
            @RequestParam Long userId) throws IOException {
        Path filePath = fileUploadService.assembleFileChunks(filename, total);
        Video video = videoService.uploadRawVideo(userId, filePath.toString());
        String encodedPath = URLEncoder.encode(video.getRawVideoPath(), StandardCharsets.UTF_8.toString());
        externalService.triggerTranscoding(video.getId(), String.format("http://localhost:5000/api/download?path=%s", encodedPath));
        return CompletableFuture.completedFuture("File uploaded");
    }

    @GetMapping
    public CompletableFuture<?> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        GetVideosResponse res = GetVideosResponse.builder()
                .videos(videos)
                .build();
        return CompletableFuture.completedFuture(res);
    }
}
