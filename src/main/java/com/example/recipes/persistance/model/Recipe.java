package com.example.recipes.persistance.model;

import javax.persistence.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String ingredients;
    private String directions;
    @Column(name = "id_category")
    private Long idCategory;
    @Column(name = "id_preparation_time")
    private Long idPreparationTime;
    @Column(name = "id_difficulty")
    private Long idDifficulty;

    protected Recipe() {
    }

    public Recipe(String title, String ingredients, String directions, Long idCategory, Long idPreparationTime, Long idDifficulty) {
        this.title = title;
        this.ingredients = ingredients;
        this.directions = directions;
        this.idCategory = idCategory;
        this.idPreparationTime = idPreparationTime;
        this.idDifficulty = idDifficulty;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", directions='" + directions + '\'' +
                ", idCategory=" + idCategory +
                ", idPreparationTime=" + idPreparationTime +
                ", idDifficulty=" + idDifficulty +
                '}';
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
