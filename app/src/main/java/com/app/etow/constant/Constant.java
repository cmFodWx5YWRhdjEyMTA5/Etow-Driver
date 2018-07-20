package com.app.etow.constant;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

public interface Constant {

    int FAIL_CONNECT_CODE = -1;
    int JSON_PARSER_CODE = -10;
    int OTHER_CODE = -20;

    String GENERIC_ERROR = "General error, please try again later";
    String SERVER_ERROR = "Fail to connect to server";

    String HOST_SCHEMA = "http://";
    String DOMAIN_NAME = "stg2.passp.asia";
    String HOST = HOST_SCHEMA + DOMAIN_NAME + "/api/";

    int TYPE_PICK_UP = 1;
    int TYPE_DROP_OFF = 2;

    String TYPE_PAYMENT_CASH = "type_payment_cash";
    String TYPE_PAYMENT_CARD = "type_payment_card";

    // Key Intent
    String TITLE_TOOLBAR = "TITLE_TOOLBAR";
    String IS_SHOW_DISTANCE = "IS_SHOW_DISTANCE";
    String TYPE_LOCATION = "TYPE_LOCATION";
    String TYPE_PAYMENT = "TYPE_PAYMENT";
}
