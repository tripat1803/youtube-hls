package com.youtube.uploader.controllers.requests;

import lombok.Builder;

@Builder
public class FileDownloadRequest {
    public String filePath;
}
