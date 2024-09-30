package com.testek.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryResponseModel {

    @JsonProperty("code")
    private int code;

    @JsonProperty("data")
    private CategoryModels data;

    // Getters và Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CategoryModels getData() {
        return data;
    }

    public void setData(CategoryModels data) {
        this.data = data;
    }
}
