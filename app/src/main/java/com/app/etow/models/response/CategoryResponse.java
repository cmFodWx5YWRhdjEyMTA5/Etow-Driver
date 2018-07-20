package com.app.etow.models.response;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.models.Category;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryResponse {

    @SerializedName("data")
    private ArrayList<Category> listCategory;

    public ArrayList<Category> getListCategory() {
        return listCategory;
    }

    public void setListCategory(ArrayList<Category> listCategory) {
        this.listCategory = listCategory;
    }
}
