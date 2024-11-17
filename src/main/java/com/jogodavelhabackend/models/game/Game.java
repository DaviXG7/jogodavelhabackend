package com.jogodavelhabackend.models.game;

import com.jogodavelhabackend.models.GameModel;
import com.jogodavelhabackend.models.User;
import com.jogodavelhabackend.models.UserModel;
import com.jogodavelhabackend.services.UserService;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
public class Game {
    private UUID id;
    private GameState state;
    private char turnOf = 'X';
    private String[][] board;
    private HashMap<String, GameUser> players;
    private UserService service;

    public Game(UserService service) {
        this.id = UUID.randomUUID();
        this.state = GameState.WAITING;
        this.board = new String[3][3];
        this.players = new HashMap<>();
        this.service = service;
    }
    public void move(int posx, int posy, String symbol) {
        this.board[posx][posy] = symbol;
        this.turnOf = this.turnOf == 'X' ? 'O' : 'X';
        checkWin();
    }
    public void addPlayer(UUID userId) {
        this.players.put(userId.toString(), new GameUser(userId, this.players.isEmpty() ? Role.X : this.players.size() == 1 ? Role.O : Role.SPECTATOR, service));
        if (players.size() == 2) {
            start();
        }

    }
    public Role getPlayerRole(String playerId) {
        return this.players.get(playerId).role();
    }
    public void removePlayer(UUID userId) {
        this.players.remove(userId);
    }
    public void start() {
        this.state = GameState.PLAYING;
    }
    public void checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                state = board[i][0].equals("X") ? GameState.X_WON : GameState.O_WON;
                return;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
                state = board[0][i].equals("X") ? GameState.X_WON : GameState.O_WON;
                return;
            }
        }

        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            state = board[0][0].equals("X") ? GameState.X_WON : GameState.O_WON;
            return;
        }
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            state = board[0][2].equals("X") ? GameState.X_WON : GameState.O_WON;
            return;
        }

        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw) {
            state = GameState.DRAW;
        }

    }
    public GameModel finish() {
        return new GameModel(this.id, this.players.values().stream().filter(gameUser -> gameUser.role().equals(Role.X)).findFirst().get().getUser(), this.players.values().stream().filter(gameUser -> gameUser.role().equals(Role.O)).findFirst().get().getUser(), this.state);
    }

    public enum Role {
        X,
        O,
        SPECTATOR
    }
}
