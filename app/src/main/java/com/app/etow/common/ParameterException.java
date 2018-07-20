package com.app.etow.common;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

public class ParameterException extends RuntimeException {
    public ParameterException() {
    }

    public ParameterException(String detailMessage) {
        super(detailMessage);
    }

    public ParameterException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ParameterException(Throwable throwable) {
        this("Parameter can not be null", throwable);
    }
}
