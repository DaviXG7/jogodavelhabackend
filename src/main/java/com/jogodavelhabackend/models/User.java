package com.jogodavelhabackend.models;



public record User(
        String id,
        String name,
        String email,
        String token,
        int games,
        int victories,
        int defeats
) {}

