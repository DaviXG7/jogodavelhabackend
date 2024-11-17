package com.jogodavelhabackend.models;

import com.jogodavelhabackend.models.game.GameState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
public class GameModel {
    @Id
    private UUID id;
    @ManyToOne
    private UserModel playerX;
    @ManyToOne
    private UserModel playerO;
    private GameState state;


    public GameModel() {}
}
