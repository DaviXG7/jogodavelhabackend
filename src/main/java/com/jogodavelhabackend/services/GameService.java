package com.jogodavelhabackend.services;

import com.jogodavelhabackend.models.game.Game;
import com.jogodavelhabackend.repositories.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final UserService service;

    private static final ConcurrentHashMap<UUID, Game> games = new ConcurrentHashMap<>();

    public Game createGame() {
        Game game = new Game(service);
        games.put(game.getId(), game);
        return game;
    }
    public Game addUser(UUID userId) {
        for (Game game : games.values()) {
            if (game.getPlayers().size() == 2) {
                continue;
            }
            game.addPlayer(userId);
            return game;
        }
        Game game = createGame();
        game.addPlayer(userId);
        return game;
    }
    public Game addUserTo(UUID gameId, UUID userId) {
        Game game = games.get(gameId);
        if (game == null) {
            return null;
        }
        game.addPlayer(userId);
        return game;
    }
    public Game move(int[] movePos, UUID gameId, UUID userId) {
        Game game = games.get(gameId);
        if (game == null) {
            return null;
        }
        game.move(movePos[0], movePos[1], game.getTurnOf() == 'X' ? "O" : "X");
        return game;
    }

}
