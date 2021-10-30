package com.pcloud.sessionsecurity.exception;

import org.springframework.security.core.AuthenticationException;

public class PatternMissMatchException extends AuthenticationException {
    public PatternMissMatchException(String explanation) {
        super(explanation);
    }
}
