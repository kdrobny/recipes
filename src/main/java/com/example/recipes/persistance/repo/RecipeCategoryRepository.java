package com.example.recipes.persistance.repo;

import com.example.recipes.persistance.model.RecipeCategory;
import org.springframework.data.repository.CrudRepository;

public interface RecipeCategoryRepository extends CrudRepository<RecipeCategory, Long> {

}
