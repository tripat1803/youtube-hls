package com.youtube.uploader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youtube.uploader.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
