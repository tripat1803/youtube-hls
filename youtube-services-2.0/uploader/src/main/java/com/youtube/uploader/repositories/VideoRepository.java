package com.youtube.uploader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youtube.uploader.models.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
