package com.jogodavelhabackend.models.game;

import com.jogodavelhabackend.models.UserModel;
import com.jogodavelhabackend.services.UserService;

import java.util.UUID;


public record GameUser(UUID id, Game.Role role, UserService service) {

    public UserModel getUser() {
        return service.findById(id);
    }
}
