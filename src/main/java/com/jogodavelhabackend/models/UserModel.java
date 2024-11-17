package com.jogodavelhabackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
public class UserModel {

    @Id
    private UUID id;
    private String name;
    private String email;
    private int accessHashcode;
    private int games;
    private int victories;
    private int defeats;

    public UserModel() {}

    public UserModel(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.accessHashcode = Objects.hash(email,password);
    }

    
}
