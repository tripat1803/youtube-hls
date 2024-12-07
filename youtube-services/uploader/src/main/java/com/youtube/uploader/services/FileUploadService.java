package com.youtube.uploader.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.uploader.utils.FileUtils;

@Service
public class FileUploadService {

    private final String FILE_PATH = "C:/Users/Lenovo/OneDrive/Desktop/LLD/youtube/youtube-services/uploader/uploads/";

    public Path uploadFileChunk(MultipartFile fileChunk, int currentChunk, int totalChunks, String filename, String ext)
            throws IOException {
        File uploadDir = new File(FILE_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Path tempFilePath = Paths.get(uploadDir.getAbsolutePath(), filename + ".part" + currentChunk + ext);
        Files.write(tempFilePath, fileChunk.getBytes(), StandardOpenOption.CREATE);

        return tempFilePath;
    }

    public boolean validateAllFileChunks(String filename, String ext, int totalChunks) {
        File uploadDir = new File(FILE_PATH);
        for (int i = 0; i < totalChunks; i++) {
            Path tempFilePath = Paths.get(uploadDir.getAbsolutePath(), filename + ".part" + i + ext);
            if (!Files.exists(tempFilePath))
                return false;
        }
        return true;
    }

    public Path assembleFileChunks(String filename, int totalChunks) throws IOException {
        File uploadDir = new File(FILE_PATH);
        Path finalFilePath = Paths.get(uploadDir.getAbsolutePath(), filename);

        CompletableFuture.runAsync(() -> {
            try {
                String[] fileArr = FileUtils.getFileDetails(filename);
        
                Files.write(finalFilePath, new byte[0], StandardOpenOption.CREATE);
        
                for (int i = 0; i < totalChunks; i++) {
                    Path tempFilePath = Paths.get(uploadDir.getAbsolutePath(), fileArr[0] + ".part" + i + fileArr[1]);
                    if (!Files.exists(tempFilePath))
                        throw new IOException("File not found");
                    byte[] fileBytes = Files.readAllBytes(tempFilePath);
                    Files.write(finalFilePath, fileBytes, StandardOpenOption.APPEND);
                    Files.delete(tempFilePath);
                }
            } catch (Exception ex) {
                throw new RuntimeException("File chunk upload failed", ex);
            }
        });

        return finalFilePath;
    }
}