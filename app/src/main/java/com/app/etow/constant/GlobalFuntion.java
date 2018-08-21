package com.app.etow.constant;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.etow.R;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.models.Setting;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.auth.SignInActivity;
import com.app.etow.ui.view_map_location.ViewMapLocationActivity;

public class GlobalFuntion {

    public static double LATITUDE = 0.0;
    public static double LONGITUDE = 0.0;
    public static Setting mSetting;

    public static void startActivity(Context context, Class<?> clz) {
        Intent intent = new Intent(context, clz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> clz, Bundle bundle, int flag) {
        Intent intent = new Intent(context, clz);
        intent.addFlags(flag);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.
                    getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public static void showMessageError(Activity activity, int code) {
        switch (code) {
            case Constant.CODE_HTTP_300:
                Toast.makeText(activity, activity.getString(R.string.msg_missing_params), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_401:
                Toast.makeText(activity, activity.getString(R.string.msg_email_existed), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_409:
                Toast.makeText(activity, activity.getString(R.string.msg__email_or_password_incorrect), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_410:
                Toast.makeText(activity, activity.getString(R.string.msg_password_missing), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_411:
                Toast.makeText(activity, activity.getString(R.string.msg_password_incorrect), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_412:
                Toast.makeText(activity, activity.getString(R.string.msg_email_does_not_exist), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_413:
                Toast.makeText(activity, activity.getString(R.string.msg_logout_failed), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_421:
                Toast.makeText(activity, activity.getString(R.string.msg_not_permission_trip), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_507:
                Toast.makeText(activity, activity.getString(R.string.msg_token_missing), Toast.LENGTH_SHORT).show();
                break;

            case Constant.CODE_HTTP_510:
                showDialogLogout(activity);
                break;

            default:
                Toast.makeText(activity, Constant.GENERIC_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showDialogDescription(Context context, String description) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_description);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);

        // Get view
        final TextView tvDescription = dialog.findViewById(R.id.tv_description);
        final TextView tvClose = dialog.findViewById(R.id.tv_close);

        tvDescription.setText(description);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void goToViewMapLocationActivity(Context context, ViewMap viewMap) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.OBJECT_VIEW_MAP, viewMap);
        startActivity(context, ViewMapLocationActivity.class, bundle);
    }

    public static void showDialogLogout(Activity activity) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(activity)
                .title(activity.getString(R.string.app_name))
                .content(activity.getString(R.string.msg_confirm_login_another_device))
                .positiveText(activity.getString(R.string.action_ok))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        DataStoreManager.setIsLogin(false);
                        DataStoreManager.setUserToken("");
                        DataStoreManager.removeUser();
                        GlobalFuntion.startActivity(activity, SignInActivity.class);
                        activity.finishAffinity();
                    }
                })
                .cancelable(false)
                .show();
    }

    public static void getCurrentLocation(Activity activity, LocationManager locationManager, boolean showMessage) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                LATITUDE = location.getLatitude();
                LONGITUDE = location.getLongitude();
                Log.e("Latitude current", LATITUDE + "");
                Log.e("Longitude current", LONGITUDE + "");
            } else {
                if (showMessage)
                    Toast.makeText(activity, activity.getString(R.string.unble_trace_location), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void showDialogNoGPS(Activity activity) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(activity)
                .title(activity.getString(R.string.app_name))
                .content(activity.getString(R.string.message_turn_on_gps))
                .positiveText(activity.getString(R.string.action_ok))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .negativeText(activity.getString(R.string.action_cancel))
                .cancelable(false)
                .show();
    }

    public static int getDistanceFromLocation(Double pickupLatitude, Double pickupLongitude,
                                              Double dropoffLatitude, Double dropoffLongitude) {
        Location pickUplocation = new Location("");
        pickUplocation.setLatitude(pickupLatitude);
        pickUplocation.setLongitude(pickupLongitude);

        Location dropOfflocation = new Location("");
        dropOfflocation.setLatitude(dropoffLatitude);
        dropOfflocation.setLongitude(dropoffLongitude);

        int distance = (int) (pickUplocation.distanceTo(dropOfflocation) / 1000);
        return distance;
    }
}