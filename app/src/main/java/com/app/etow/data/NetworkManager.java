package com.app.etow.data;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.networking.ThinkFitService;
import com.app.etow.models.response.ApiResponse;
import com.app.etow.models.response.ApiSuccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class NetworkManager {

    private final ThinkFitService mThinkFitService;

    @Inject
    public NetworkManager(ThinkFitService thinkFitService) {
        this.mThinkFitService = thinkFitService;
    }

    public Observable<ApiResponse> login(String email, String password) {
        return mThinkFitService.login(email, password);
    }

    public Observable<ApiSuccess> logout(String token) {
        return mThinkFitService.logout(token);
    }
}
