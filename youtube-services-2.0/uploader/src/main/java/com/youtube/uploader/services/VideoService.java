package com.youtube.uploader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtube.uploader.models.User;
import com.youtube.uploader.models.Video;
import com.youtube.uploader.repositories.VideoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class VideoService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private VideoRepository videoRepository;

    public void uploadRawVideo(Long userId, String rawVideoPath) {
        User user = entityManager.getReference(User.class, userId);
        Video video = Video.builder()
                .user(user)
                .rawVideoPath(rawVideoPath)
                .build();
        videoRepository.save(video);
    }

    public void uploadTranscodedVideo(Long videoId, String transcodedVideoPath) {
        Video video = entityManager.getReference(Video.class, videoId);
        video.setTranscodedVideoPath(transcodedVideoPath);
        videoRepository.save(video);
    }
}
