package com.app.etow.data.prefs;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.content.Context;

public class DataStoreManager {

    public static final String PREF_FIRST_INSTALL_APP = "PREF_FIRST_INSTALL_APP";

    private static DataStoreManager instance;
    private MySharedPreferences sharedPreferences;

    /**
     * Call when start application
     */
    public static void init(Context context) {
        instance = new DataStoreManager();
        instance.sharedPreferences = new MySharedPreferences(context);
    }

    public static DataStoreManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    public static void setFirstInstallApp(boolean status) {
        DataStoreManager.getInstance().sharedPreferences.putBooleanValue(PREF_FIRST_INSTALL_APP, status);
    }

    public static boolean getFirstInstallApp() {
        return DataStoreManager.getInstance().sharedPreferences.getBooleanValue(PREF_FIRST_INSTALL_APP);
    }
}
