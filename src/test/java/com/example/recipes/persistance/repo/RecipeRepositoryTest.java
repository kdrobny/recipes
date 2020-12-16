package com.example.recipes.persistance.repo;

import com.example.recipes.persistance.model.Recipe;
import com.example.recipes.persistance.model.RecipeCategory;
import com.example.recipes.persistance.model.RecipeDifficulty;
import com.example.recipes.persistance.model.RecipePreparationTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void create() {
        // given
        RecipeCategory recipeCategory = new RecipeCategory();
        recipeCategory.setId(1L);
        RecipeDifficulty recipeDifficulty = new RecipeDifficulty();
        recipeDifficulty.setId(1L);
        RecipePreparationTime recipePreparationTime = new RecipePreparationTime();
        recipePreparationTime.setId(1L);

        Recipe recipe = new Recipe("Test Recipe", "Ingredients", "Directions", recipeCategory, recipePreparationTime, recipeDifficulty);

        entityManager.persist(recipe);
        entityManager.flush();

        // when
        List<Recipe> testRecipeList = recipeRepository.findByTitle("Test Recipe");

        // then
        assertTrue(testRecipeList != null && testRecipeList.size() > 0
                && testRecipeList.get(0) != null
                && "Test Recipe".equals(testRecipeList.get(0).getTitle()));
    }

    @Test
    void findByTitle() {
        // when
        List<Recipe> recipeList = recipeRepository.findByTitle("Ratatouille");

        // then
        assertTrue(recipeList != null && recipeList.size() > 0);
    }

    @Test
    void findById() {
        // when
        List<Recipe> recipeList = recipeRepository.findByTitle("Ratatouille");
        assertTrue(recipeList != null && recipeList.size() > 0);

        Recipe recipe = recipeRepository.findById(recipeList.get(0).getId()).orElse(null);

        // then
        assert(recipe != null && "Ratatouille".equals(recipe.getTitle()));
    }

    @Test
    @Transactional
    void update() {
        // given
        RecipeCategory recipeCategory = new RecipeCategory();
        recipeCategory.setId(2L);
        RecipeDifficulty recipeDifficulty = new RecipeDifficulty();
        recipeDifficulty.setId(2L);
        RecipePreparationTime recipePreparationTime = new RecipePreparationTime();
        recipePreparationTime.setId(2L);

        List<Recipe> testRecipeList = recipeRepository.findByTitle("Minestrone");
        assertTrue(testRecipeList != null && testRecipeList.size() > 0
                && testRecipeList.get(0) != null);

        Recipe recipe = testRecipeList.get(0);
        recipe.setTitle("Minestrone Update");
        recipe.setRecipeCategory(recipeCategory);
        recipe.setRecipeDifficulty(recipeDifficulty);
        recipe.setRecipePreparationTime(recipePreparationTime);

        entityManager.persist(recipe);
        entityManager.flush();

        // when
        recipe = recipeRepository.findById(recipe.getId()).orElse(null);

        // then
        assertTrue(recipe != null && "Minestrone Update".equals(recipe.getTitle())
                && recipe.getRecipeCategory().getId() == 2L
                && recipe.getRecipeDifficulty().getId() == 2L
                && recipe.getRecipePreparationTime().getId() == 2L
        );
    }

    @Test
    @Transactional
    void delete() {
        // given
        List<Recipe> testRecipeList = recipeRepository.findByTitle("Srdce na smotane");
        assertTrue(testRecipeList != null && testRecipeList.size() > 0
                && testRecipeList.get(0) != null);

        Recipe recipe = testRecipeList.get(0);
        recipeRepository.delete(recipe);
        entityManager.flush();

        // when
        testRecipeList = recipeRepository.findByTitle("Srdce na smotane");

        // then
        assertTrue(testRecipeList != null && testRecipeList.size() == 0);
    }

}