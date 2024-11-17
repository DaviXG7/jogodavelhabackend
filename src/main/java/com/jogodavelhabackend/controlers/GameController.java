package com.jogodavelhabackend.controlers;

import com.jogodavelhabackend.models.game.Game;
import com.jogodavelhabackend.models.game.GameState;
import com.jogodavelhabackend.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class GameController {

    private final GameService service;

    record GameResponse(String gameId, String playerId, String playerRole, GameState state) {}
    record GameRequest(String gameId, String playerid, int[] move) {}

    @MessageMapping("/move/{id}")
    @SendTo("/response/move/{id}")
    public GameResponse onGameMove(@DestinationVariable String id, GameRequest request)  {
        Game game = service.move(request.move, UUID.fromString(id), UUID.fromString(request.playerid));
        return new GameResponse(id, request.playerid, game.getPlayerRole(request.playerid).name(), game.getState());
    }

    @MessageMapping("/join")
    @SendTo("/response/join")
    public GameResponse joinGameWithoutId(@RequestBody GameRequest request) {

        System.out.println(request);

        Game game = service.addUser(UUID.fromString(request.playerid));

        return new GameResponse(game.getId().toString(), request.playerid, game.getPlayerRole(request.playerid).name(), game.getState());
    }

    @MessageMapping("/join/{id}")
    @SendTo("/response/join/{id}")
    public GameResponse joinGame(@DestinationVariable String id, GameRequest request) {
        Game game = service.addUserTo(UUID.fromString(id), UUID.fromString(request.playerid));
        return new GameResponse(id, request.playerid, game.getPlayerRole(request.playerid).name(), game.getState());
    }

}
