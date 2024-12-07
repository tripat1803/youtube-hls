package com.youtube.uploader.controllers.requests;

import com.youtube.uploader.utils.enums.Provider;

public class CreateUserRequest {
    public String firstname;
    public String lastname;
    public String providerId;
    public Provider providerType;
}
