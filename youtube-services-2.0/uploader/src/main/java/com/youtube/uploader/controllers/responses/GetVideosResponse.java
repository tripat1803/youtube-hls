package com.youtube.uploader.controllers.responses;

import java.util.List;

import com.youtube.uploader.models.Video;

import lombok.Builder;

@Builder
public class GetVideosResponse {
    public List<Video> videos;
}
