package com.example.recipes.persistance.repo;

import com.example.recipes.persistance.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long>  {

    List<Recipe> findByTitle(String title);

    Recipe findById(long id);
}
