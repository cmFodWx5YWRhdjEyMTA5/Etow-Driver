package com.app.etow.constant;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.app.etow.R;
import com.app.etow.ui.view_map_location.ViewMapLocationActivity;

public class GlobalFuntion {

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
            /*case Constant.CODE_HTTP_401:
                Toast.makeText(activity, activity.getString(R.string.msg_error_login_with_email),
                 Toast.LENGTH_SHORT).show();
                break;*/

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

    public static void goToViewMapLocationActivity(Context context, String titleToolbar,
                                                   boolean isShowDistance, int typeLocation) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_TOOLBAR, titleToolbar);
        bundle.putBoolean(Constant.IS_SHOW_DISTANCE, isShowDistance);
        bundle.putInt(Constant.TYPE_LOCATION, typeLocation);
        startActivity(context, ViewMapLocationActivity.class, bundle);
    }
}