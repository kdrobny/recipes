package com.example.recipes.persistance.repo;

import com.example.recipes.persistance.model.RecipePreparationTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipePreparationTimeRepository extends JpaRepository<RecipePreparationTime, Long> {

}
