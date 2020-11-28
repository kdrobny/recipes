package com.example.recipes.persistance.repo;

import com.example.recipes.persistance.model.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {

}
