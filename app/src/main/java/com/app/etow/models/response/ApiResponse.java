package com.app.etow.models.response;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiResponse {

    @SerializedName("data")
    private JsonObject data;


    public <T> T getDataObject(Class<T> tClass) {
        JSONObject object = null;
        try {
            object = new JSONObject(data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        T obj = new GsonBuilder().create().fromJson(object.toString(), tClass);
        return obj;
    }


    public <T> ArrayList<T> getDataArray(Class<T> tClass) {
        ArrayList<T> listObj = new ArrayList<>();
        JSONArray arr = null;
        try {
            arr = new JSONArray(data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data != null) {
            JSONObject jo;
            T object;
            int size = arr.length();
            Gson gson = new GsonBuilder().create();
            for (int i = 0; i < size; i++) {
                jo = arr.optJSONObject(i);
                if (jo != null) {
                    object = gson.fromJson(jo.toString(), tClass);
                    listObj.add(object);
                }
            }
        }
        return listObj;
    }
}
