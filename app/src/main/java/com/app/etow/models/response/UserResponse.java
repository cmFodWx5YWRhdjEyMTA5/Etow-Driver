package com.app.etow.models.response;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.models.User;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("users")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
