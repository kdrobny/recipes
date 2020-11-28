package com.example.recipes.persistance.repo;

import com.example.recipes.persistance.model.RecipeDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeDifficultyRepository extends JpaRepository<RecipeDifficulty, Long> {

}
