package com.stbegradleapp.fixer.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class LoginRequest {
    private final String username, password;
}
