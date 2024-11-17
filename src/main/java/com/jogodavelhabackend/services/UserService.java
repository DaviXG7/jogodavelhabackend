package com.jogodavelhabackend.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jogodavelhabackend.models.User;
import com.jogodavelhabackend.models.UserModel;
import com.jogodavelhabackend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public final Cache<String, User> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(4, TimeUnit.HOURS).build();

    public void save(UserModel user) {
        userRepository.save(user);
    }
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    public UserModel findByHashCode(String email, String password) {
        return userRepository.findByAccessHashcode(Objects.hash(email,password)).orElse(null);
    }
    public UserModel findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
    public void cacheUser(User user) {
        cache.put(user.token(), user);
    }


}
