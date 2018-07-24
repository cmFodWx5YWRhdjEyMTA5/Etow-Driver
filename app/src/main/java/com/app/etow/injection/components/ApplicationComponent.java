package com.app.etow.injection.components;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.app.etow.data.NetworkManager;
import com.app.etow.injection.ApplicationContext;
import com.app.etow.injection.modules.ApplicationModule;
import com.app.etow.ui.base.BaseActivity;
import com.app.etow.ui.base.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    Retrofit retrofit();

    NetworkManager networkManager();

    Toast toast();

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment fragment);
}
