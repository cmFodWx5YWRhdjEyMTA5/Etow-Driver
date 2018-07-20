package com.app.etow.ui.base;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

public interface Presenter<V extends MvpView> {

    void initialView(V mvpView);

    void destroyView();

    void destroy();
}
