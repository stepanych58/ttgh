package com.stbegradleapp.fixer.exceptions;

import java.math.BigInteger;
import java.text.MessageFormat;

public class FixerUserException extends RuntimeException {
    public static final String USER_NOT_FOUND = "User {0} not found";
    public FixerUserException(String message, BigInteger userId) {
        super(MessageFormat.format(message, userId));
    }
}
