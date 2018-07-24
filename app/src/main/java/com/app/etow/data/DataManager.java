package com.app.etow.data;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.models.response.ApiResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {

    private final NetworkManager mNetworkManager;

    @Inject
    public DataManager(NetworkManager networkManager) {
        this.mNetworkManager = networkManager;
    }

    public NetworkManager getNetworkManager() {
        return mNetworkManager;
    }

    public Observable<ApiResponse> getUser(String email, String password) {
        return getNetworkManager().getUser(email, password);
    }
}
