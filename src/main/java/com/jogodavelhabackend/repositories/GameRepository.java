package com.jogodavelhabackend.repositories;

import com.jogodavelhabackend.models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<GameModel, UUID> {

}
