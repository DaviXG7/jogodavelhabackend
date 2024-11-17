package com.jogodavelhabackend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpServerErrorException;

@Getter
public class UserException extends HttpServerErrorException {

    private final UserExceptionType type;

    public UserException(UserExceptionType type) {
        super(HttpStatusCode.valueOf(500), type.getMessage());
        this.type = type;
    }
}
