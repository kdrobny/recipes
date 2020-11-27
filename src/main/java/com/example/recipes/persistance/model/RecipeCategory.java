package com.example.recipes.persistance.model;

import javax.persistence.*;

@Entity
public class RecipeCategory extends Enum {

    public RecipeCategory() {
    }

    public RecipeCategory(String desc, String valid, Long ord) {
        super(desc, valid, ord);
    }

}
