package com.app.etow.constant;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

public interface Constant {

    String SUCCESS = "success";

    int FAIL_CONNECT_CODE = -1;
    int JSON_PARSER_CODE = -10;
    int OTHER_CODE = -20;

    int CODE_HTTP_300 = 300;
    int CODE_HTTP_401 = 401;
    int CODE_HTTP_409 = 409;
    int CODE_HTTP_410 = 410;
    int CODE_HTTP_411 = 411;
    int CODE_HTTP_412 = 412;
    int CODE_HTTP_413 = 413;
    int CODE_HTTP_421 = 421;
    int CODE_HTTP_507 = 507;
    int CODE_HTTP_510 = 510;

    String GENERIC_ERROR = "General error, please try again later";
    String SERVER_ERROR = "Fail to connect to server";

    String HOST_SCHEMA = "http://";
    String DOMAIN_NAME = "suusoft.com/eTow/public";
    String HOST = HOST_SCHEMA + DOMAIN_NAME + "/api/v1/";

    int TYPE_PICK_UP = 1;
    int TYPE_DROP_OFF = 2;

    String TYPE_PAYMENT_CASH = "cash";
    String TYPE_PAYMENT_CARD = "card";

    String TYPE_VEHICLE_NORMAL = "normal";
    String TYPE_VEHICLE_FLATBED = "flatbed";

    String IS_ONLINE = "1";
    String IS_OFFLINE = "0";

    String IS_SCHEDULE = "1";
    String SCHEDULE_TRIP_STATUS_NEW = "1_1";
    String SCHEDULE_TRIP_STATUS_ACCEPT = "4_1";

    String NORMAL_TRIP_STATUS_NEW = "1_0";

    // Key Intent
    String OBJECT_TRIP = "OBJECT_TRIP";
    String OBJECT_VIEW_MAP = "OBJECT_VIEW_MAP";
    String IS_TRIP_GOING = "IS_TRIP_GOING";

    String TRIP_STATUS_NEW = "1"; // Khi user mới booking trip
    String TRIP_STATUS_CANCEL = "2"; // Khi user cancel trip
    String TRIP_STATUS_REJECT = "3"; // Khi driver reject trip
    String TRIP_STATUS_ACCEPT = "4"; // Khi driver accept trip
    String TRIP_STATUS_ARRIVED = "5"; // Khi driver đến pick up location
    String TRIP_STATUS_ON_GOING = "6"; // Khi driver dang di đến drop off location
    String TRIP_STATUS_JOURNEY_COMPLETED = "7"; // Khi driver đến drop off location
    String TRIP_STATUS_COMPLETE = "8"; // Khi thanh toán xong
}
