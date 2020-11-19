package com.example.recipes.rest.dto;

public class RecipeDto {

    private Long id;
    private String title;
    private String ingredients;
    private String directions;
    private Long idCategory;
    private Long idPreparationTime;
    private Long idDifficulty;

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

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdPreparationTime() {
        return idPreparationTime;
    }

    public void setIdPreparationTime(Long idPreparationTime) {
        this.idPreparationTime = idPreparationTime;
    }

    public Long getIdDifficulty() {
        return idDifficulty;
    }

    public void setIdDifficulty(Long idDifficulty) {
        this.idDifficulty = idDifficulty;
    }

}
