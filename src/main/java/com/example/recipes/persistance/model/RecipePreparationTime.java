package com.example.recipes.persistance.model;

import javax.persistence.Entity;

@Entity
public class RecipePreparationTime extends Enum {

    public RecipePreparationTime() {
    }

    public RecipePreparationTime(String desc, String valid, Long ord) {
        super(desc, valid, ord);
    }

}
