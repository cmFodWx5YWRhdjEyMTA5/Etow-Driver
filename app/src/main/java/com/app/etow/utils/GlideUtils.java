package com.app.etow.utils;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class GlideUtils {

    public static void loadUrl(String url, ImageView imageView) {
        if (StringUtil.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .into(imageView);
    }

    public static void loadFile(File file, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(file)
                .into(imageView);
    }

    public static void loadBase64(String base64, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(Base64.decode(base64, Base64.DEFAULT))
                .into(imageView);
    }
}