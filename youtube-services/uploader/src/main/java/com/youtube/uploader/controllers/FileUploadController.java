package com.youtube.uploader.controllers;

import java.util.concurrent.CompletableFuture;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.uploader.services.FileUploadService;
import com.youtube.uploader.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@RestController
@RequestMapping(path = "/api/file")
@Async
@Slf4j
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    public CompletableFuture<?> uploadCunks(@RequestPart MultipartFile fileChunk, @RequestParam String filename,
            @RequestParam int index,
            @RequestParam int total) throws IOException {
        String[] fileArr = FileUtils.getFileDetails(filename);
        Path path = fileUploadService.uploadFileChunk(fileChunk, index, total, fileArr[0], fileArr[1]);
        return CompletableFuture.completedFuture(path);
    }
}
