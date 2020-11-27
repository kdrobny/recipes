package com.example.recipes.persistance.model;

import javax.persistence.Entity;

@Entity
public class RecipeDifficulty extends Enum {

    public RecipeDifficulty() {
    }

    public RecipeDifficulty(String desc, String valid, Long ord) {
        super(desc, valid, ord);
    }

}
