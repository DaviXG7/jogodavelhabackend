package com.jogodavelhabackend.controlers;

import com.jogodavelhabackend.models.game.Game;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@AllArgsConstructor
public class GameControler {





    @MessageMapping("/move/{id}")
    @SendTo("/response/move/{id}")
    public GameResponse onGameMove(@DestinationVariable String id, PlayerMoveMessage move)  {
        return gameService.makeMove(id, move);
    }

    @MessageMapping("/join/{id}")
    @SendTo("/response/join/{id}")
    public GameResponse onGameJoin(@DestinationVariable String id, @Header("simpSessionId") String sessionId, PlayerJoinMessage move)  {
        return gameService.joinGame(sessionId, id, move);
    }

}
