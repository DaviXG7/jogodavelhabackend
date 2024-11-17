package com.jogodavelhabackend.controlers;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jogodavelhabackend.exceptions.UserException;
import com.jogodavelhabackend.exceptions.UserExceptionType;
import com.jogodavelhabackend.models.User;
import com.jogodavelhabackend.models.UserModel;
import com.jogodavelhabackend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;

    record LoginAuthRequest(String email, String password) {}
    record RegisterAuthRequest(String name, String email, String password) {}

    @PostMapping("/validate")
    public boolean validateToken(@RequestBody String token) {

        return userService.cache.asMap().containsKey(JsonParser.parseString(token).getAsJsonObject().get("token").getAsString());
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginAuthRequest authRequest) {
        UserModel user = userService.findByHashCode(authRequest.email(), authRequest.password());
        if (user == null) {
            throw new UserException(UserExceptionType.USER_NOT_FOUND);
        }
        User userResponse = new User(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                userService.generateToken(),
                user.getGames(),
                user.getVictories(),
                user.getDefeats()
        );

        userService.cacheUser(userResponse);

        return userResponse;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterAuthRequest authRequest) {
        UserModel user = new UserModel(
                authRequest.name,
                authRequest.email,
                authRequest.password
        );
        if (userService.existsEmail(user.getEmail())) {
            throw new UserException(UserExceptionType.EMAIL_ALREADY_EXISTS);
        }
        userService.save(user);
        User userResponse = new User(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                userService.generateToken(),
                user.getGames(),
                user.getVictories(),
                user.getDefeats()
        );

        userService.cacheUser(userResponse);

        return userResponse;
    }
}
