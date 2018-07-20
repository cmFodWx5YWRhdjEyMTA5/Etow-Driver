package com.app.etow.ui.widget.textview;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.app.etow.utils.Utils;

public class TahomaBoldTextView extends AppCompatTextView {
    public TahomaBoldTextView(Context context) {
        super(context);
        setFont();
    }

    public TahomaBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public TahomaBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }


    private void setFont() {
        Typeface textViewTypeface = Utils.getTahomaBoldTypeFace(getContext());
        setTypeface(textViewTypeface);
    }
}
