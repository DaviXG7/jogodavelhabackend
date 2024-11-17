package com.jogodavelhabackend.exceptions;

import lombok.Getter;

@Getter
public enum UserExceptionType {

    USER_NOT_FOUND("Usuário não encontrado. Verifique o email e a senha"),
    USER_ALREADY_EXISTS("Usuário já existe"),
    EMAIL_ALREADY_EXISTS("Email já cadastrado"),
    USER_NOT_AUTHORIZED("Usuário não autorizado"),
    USER_NOT_LOGGED_IN("Usuário não logado");

    private final String message;

    UserExceptionType(String message) {
        this.message = message;
    }

}
