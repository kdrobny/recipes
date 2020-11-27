package com.example.recipes.persistance.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Enum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String desc;

    private String valid;

    private Long ord;

    public Enum() {
    }

    public Enum(String desc, String valid, Long ord) {
        this.desc = desc;
        this.valid = valid;
        this.ord = ord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Long getOrd() {
        return ord;
    }

    public void setOrd(Long ord) {
        this.ord = ord;
    }

    @Override
    public String toString() {
        return "Enum{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", valid='" + valid + '\'' +
                ", ord=" + ord +
                '}';
    }

}
