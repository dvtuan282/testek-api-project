package com.testek.api.models;

import java.util.UUID;

public class CategoryModels {
    private String id;
    private String cateDesc;
    private String categoryName;
    private String status;

    public CategoryModels() {
    }

    public CategoryModels(String cateDesc, String categoryName, String status) {
        this.cateDesc = cateDesc;
        this.categoryName = categoryName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getcateDesc() {
        return cateDesc;
    }

    public void setcateDesc(String cateDesc) {
        this.cateDesc = cateDesc;
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
