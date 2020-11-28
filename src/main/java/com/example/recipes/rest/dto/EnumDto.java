package com.example.recipes.rest.dto;

public class EnumDto {
    private Long id;
    private String desc;
    private String valid;
    private Long ord;

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
}
