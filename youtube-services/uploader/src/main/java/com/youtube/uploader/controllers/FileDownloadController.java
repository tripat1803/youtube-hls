package com.youtube.uploader.controllers;

import java.util.concurrent.CompletableFuture;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@RestController
@RequestMapping(path = "/api/download")
@Async
public class FileDownloadController {

    @GetMapping
    public CompletableFuture<?> downloadFile(@RequestParam String path, HttpServletRequest req) throws IOException {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        final InputStreamResource resource = new InputStreamResource(inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LAST_MODIFIED, String.valueOf(file.lastModified()));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        ResponseEntity<?> res = ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
        return CompletableFuture.completedFuture(res);
    }
}
