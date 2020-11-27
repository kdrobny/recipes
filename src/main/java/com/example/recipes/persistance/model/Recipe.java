package com.example.recipes.persistance.model;

import javax.persistence.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(name = "INGREDIENTS", length = 4000)
    private String ingredients;

    @Column(name = "DIRECTIONS", length = 4000)
    private String directions;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORY", referencedColumnName = "ID")
    private RecipeCategory recipeCategory;

    @ManyToOne
    @JoinColumn(name = "ID_PREPARATION_TIME", referencedColumnName = "ID")
    private RecipePreparationTime recipePreparationTime;

    @ManyToOne
    @JoinColumn(name = "ID_DIFFICULTY", referencedColumnName = "ID")
    private RecipeDifficulty recipeDifficulty;

    protected Recipe() {
    }

    public Recipe(String title, String ingredients, String directions, RecipeCategory recipeCategory, RecipePreparationTime recipePreparationTime, RecipeDifficulty recipeDifficulty) {
        this.title = title;
        this.ingredients = ingredients;
        this.directions = directions;
        this.recipeCategory = recipeCategory;
        this.recipePreparationTime = recipePreparationTime;
        this.recipeDifficulty = recipeDifficulty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public RecipeCategory getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(RecipeCategory recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public RecipePreparationTime getRecipePreparationTime() {
        return recipePreparationTime;
    }

    public void setRecipePreparationTime(RecipePreparationTime recipePreparationTime) {
        this.recipePreparationTime = recipePreparationTime;
    }

    public RecipeDifficulty getRecipeDifficulty() {
        return recipeDifficulty;
    }

    public void setRecipeDifficulty(RecipeDifficulty recipeDifficulty) {
        this.recipeDifficulty = recipeDifficulty;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", directions='" + directions + '\'' +
                ", recipeCategory=" + recipeCategory +
                ", recipePreparationTime=" + recipePreparationTime +
                ", recipeDifficulty=" + recipeDifficulty +
                '}';
    }

}
