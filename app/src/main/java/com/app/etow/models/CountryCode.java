package com.app.etow.models;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/07/12
 * ******************************************************************************
 */

public class CountryCode {

    private String name;
    private String dialCode;
    private String code;

    public CountryCode(String name, String dialCode, String code) {
        this.name = name;
        this.dialCode = dialCode;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
