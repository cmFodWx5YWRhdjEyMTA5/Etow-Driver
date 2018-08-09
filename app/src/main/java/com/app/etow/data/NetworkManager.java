package com.app.etow.data;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.networking.EtowService;
import com.app.etow.models.response.ApiResponse;
import com.app.etow.models.response.ApiSuccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class NetworkManager {

    private final EtowService mEtowService;

    @Inject
    public NetworkManager(EtowService etowService) {
        this.mEtowService = etowService;
    }

    public Observable<ApiResponse> login(String email, String password) {
        return mEtowService.login(email, password);
    }

    public Observable<ApiSuccess> logout(String token) {
        return mEtowService.logout(token);
    }

    public Observable<ApiSuccess> sendFeedback(String comment) {
        return mEtowService.sendFeedback(comment);
    }

    public Observable<ApiResponse> updateProfile(String isOnline) {
        return mEtowService.updateProfile(isOnline);
    }

    public Observable<ApiSuccess> updateTrip(int tripId, String status) {
        return mEtowService.updateTrip(tripId, status);
    }
}
