package com.youtube.uploader.controllers.responses;

import java.util.List;

import com.youtube.uploader.models.User;

import lombok.Builder;

@Builder
public class GetUsersResponse {
    public List<User> users;
}
