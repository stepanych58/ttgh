package com.stbegradleapp.fixer.config.jwt;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExpiredTokensCashe {
    private Map<String, String> expiredTokens = new HashMap<>();

    public void addToken(String token, String userId) {
        expiredTokens.put(token, userId);
    }

    public boolean isExpire(String token) {
        return expiredTokens.containsKey(token);
    }

    public String getTokenFor(String token) {
        return expiredTokens.get(token);
    }

}
