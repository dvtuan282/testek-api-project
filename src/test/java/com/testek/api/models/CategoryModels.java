package com.testek.api.models;

public class CategoryModels {
    private String cateDes;
    private String categoryName;
    private String status;

    public CategoryModels() {
    }

    public CategoryModels(String cateDes, String categoryName, String status) {
        this.cateDes = cateDes;
        this.categoryName = categoryName;
        this.status = status;
    }

    public String getCateDes() {
        return cateDes;
    }

    public void setCateDes(String cateDes) {
        this.cateDes = cateDes;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
