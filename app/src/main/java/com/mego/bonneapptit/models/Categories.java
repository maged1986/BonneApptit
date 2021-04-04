package com.mego.bonneapptit.models;

import java.util.ArrayList;

public class Categories {
    private String CategoryName;
    private int imageUrl;

    public Categories(String categoryName, int imageUrl) {
        CategoryName = categoryName;
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}