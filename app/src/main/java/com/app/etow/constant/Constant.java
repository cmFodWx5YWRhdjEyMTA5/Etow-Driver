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

    String FILTER_NONE = "0";
    String FILTER_CASH = "1";
    String FILTER_CARD = "2";

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

    String PAYMENT_STATUS_PAYMENT_SUCCESS = "success"; // Thanh toán thanh cong
    String PAYMENT_STATUS_PAYMENT_PENDING = "pending"; // Thanh toán thanh cong
    String PAYMENT_STATUS_PAYMENT_FAIL = "fail"; // Thanh toán that bai

    String JSON = "{\n" +
            "   \"geocoded_waypoints\" : [\n" +
            "      {\n" +
            "         \"geocoder_status\" : \"OK\",\n" +
            "         \"place_id\" : \"ChIJ4WWeD8isNTER5PFhfXxllNA\",\n" +
            "         \"types\" : [ \"street_address\" ]\n" +
            "      },\n" +
            "      {\n" +
            "         \"geocoder_status\" : \"OK\",\n" +
            "         \"place_id\" : \"ChIJbz9hIKGsNTERICI4KhW4qhE\",\n" +
            "         \"types\" : [ \"political\", \"sublocality\", \"sublocality_level_1\" ]\n" +
            "      }\n" +
            "   ],\n" +
            "   \"routes\" : [\n" +
            "      {\n" +
            "         \"bounds\" : {\n" +
            "            \"northeast\" : {\n" +
            "               \"lat\" : 21.0106289,\n" +
            "               \"lng\" : 105.7995457\n" +
            "            },\n" +
            "            \"southwest\" : {\n" +
            "               \"lat\" : 20.9890395,\n" +
            "               \"lng\" : 105.7902824\n" +
            "            }\n" +
            "         },\n" +
            "         \"copyrights\" : \"Map data ©2018 Google\",\n" +
            "         \"legs\" : [\n" +
            "            {\n" +
            "               \"distance\" : {\n" +
            "                  \"text\" : \"3.7 km\",\n" +
            "                  \"value\" : 3711\n" +
            "               },\n" +
            "               \"duration\" : {\n" +
            "                  \"text\" : \"12 mins\",\n" +
            "                  \"value\" : 738\n" +
            "               },\n" +
            "               \"end_address\" : \"Trung Hoà, Cầu Giấy, Hanoi, Vietnam\",\n" +
            "               \"end_location\" : {\n" +
            "                  \"lat\" : 21.0101462,\n" +
            "                  \"lng\" : 105.798849\n" +
            "               },\n" +
            "               \"start_address\" : \"62 Phùng Khoang 1, P. Văn Quán, Từ Liêm, Hà Nội, Vietnam\",\n" +
            "               \"start_location\" : {\n" +
            "                  \"lat\" : 20.9890395,\n" +
            "                  \"lng\" : 105.7930058\n" +
            "               },\n" +
            "               \"steps\" : [\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.4 km\",\n" +
            "                        \"value\" : 450\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"2 mins\",\n" +
            "                        \"value\" : 138\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 20.9918076,\n" +
            "                        \"lng\" : 105.7953164\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Head \\u003cb\\u003enorth\\u003c/b\\u003e on \\u003cb\\u003ePhùng Khoang\\u003c/b\\u003e toward \\u003cb\\u003eTô Hiệu\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by BÉO SHOP (on the right)\\u003c/div\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"olb_CiuudSE@{@HA@A?A?AAAACAAAAAGMIUGOEEGEA?AAC?C@G?a@Nc@iAGQy@Xa@gAWq@IIQOo@e@c@k@CCEE}AoB\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 20.9890395,\n" +
            "                        \"lng\" : 105.7930058\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.6 km\",\n" +
            "                        \"value\" : 616\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"2 mins\",\n" +
            "                        \"value\" : 134\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 20.994742,\n" +
            "                        \"lng\" : 105.7902824\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e at Tiệm Bánh Pháp onto \\u003cb\\u003eLương Thế Vinh\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Cửa Hàng Kim Khí Tổng Hợp Hà Trường (on the right)\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"y}b_CwcvdS[n@aAnBMXq@vAYf@aAlB[r@INaArBWf@S^Ud@c@z@u@~AMVm@rAEHIN\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 20.9918076,\n" +
            "                        \"lng\" : 105.7953164\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.8 km\",\n" +
            "                        \"value\" : 845\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"2 mins\",\n" +
            "                        \"value\" : 149\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 20.9992358,\n" +
            "                        \"lng\" : 105.7968459\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e at Công ty TNHH Thương mại 98 onto \\u003cb\\u003eTố Hữu\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Công ty cổ phần công nghệ dịch vụ thương mại ANA (on the right in 550&nbsp;m)\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"cpc_CgdudS_I_NKQsAwBmE_HqAqB]m@SUIM{AsCk@_A\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 20.994742,\n" +
            "                        \"lng\" : 105.7902824\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.7 km\",\n" +
            "                        \"value\" : 718\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"2 mins\",\n" +
            "                        \"value\" : 115\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 21.0043065,\n" +
            "                        \"lng\" : 105.7933053\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e at Showroom Nội Thất Bếp Esmart onto \\u003cb\\u003eKhuất Duy Tiến\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Blue Lotus Coffee (on the right)\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"gld_CimvdSq@eA[Tu@n@}AfA_Ar@mCnBqEhDwAlAi@\\\\g@^sDrCUL\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 20.9992358,\n" +
            "                        \"lng\" : 105.7968459\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"1.0 km\",\n" +
            "                        \"value\" : 963\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"3 mins\",\n" +
            "                        \"value\" : 172\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 21.0103546,\n" +
            "                        \"lng\" : 105.7994399\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eTrần Duy Hưng\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Công Ty Tnhh Tm Quảng Cáo Xd Địa Ốc Việt Hàn (on the right in 700&nbsp;m)\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"}ke_CewudSUFUDQ@M?MAmCm@]Mk@UW[W]eEcGGIEEGCeAaBW_@cA{AoAaBmAcBq@cAq@iAwAqByAeBc@i@\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 21.0043065,\n" +
            "                        \"lng\" : 105.7933053\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.1 km\",\n" +
            "                        \"value\" : 119\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 30\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 21.0101462,\n" +
            "                        \"lng\" : 105.798849\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Make a \\u003cb\\u003eU-turn\\u003c/b\\u003e at Tan Viet Tien Production Service Trading Co., Ltd\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Cửa Hàng Tạp Hóa Thanh Hằng (on the right)\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"uturn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"uqf_Co}vdSSUc@^TVhArA\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 21.0103546,\n" +
            "                        \"lng\" : 105.7994399\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  }\n" +
            "               ],\n" +
            "               \"traffic_speed_entry\" : [],\n" +
            "               \"via_waypoint\" : []\n" +
            "            }\n" +
            "         ],\n" +
            "         \"overview_polyline\" : {\n" +
            "            \"points\" : \"olb_CiuudSeALIEUg@MUIEQ?a@Nc@iAGQy@Xy@yB[Yo@e@c@k@II}AoB[n@oAhCiD`HuFdLs@|AIN_I_N_BiC_HqKq@cAeBaD}AeCqAdA}NtKaCjB{ErDULUFg@F[AmCm@]Mk@UW[W]mEmGMI}AaCcA{AoAaB_CgDq@iAwAqB}BoCSUc@^~AjB\"\n" +
            "         },\n" +
            "         \"summary\" : \"Trần Duy Hưng\",\n" +
            "         \"warnings\" : [],\n" +
            "         \"waypoint_order\" : []\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}";

    String JSON2 = "{\n" +
            "   \"geocoded_waypoints\" : [\n" +
            "      {\n" +
            "         \"geocoder_status\" : \"OK\",\n" +
            "         \"place_id\" : \"ChIJbz9hIKGsNTERICI4KhW4qhE\",\n" +
            "         \"types\" : [ \"political\", \"sublocality\", \"sublocality_level_1\" ]\n" +
            "      },\n" +
            "      {\n" +
            "         \"geocoder_status\" : \"OK\",\n" +
            "         \"place_id\" : \"ChIJC45fzClTNDERdQJ7elaOzS8\",\n" +
            "         \"types\" : [ \"bank\", \"establishment\", \"finance\", \"point_of_interest\" ]\n" +
            "      }\n" +
            "   ],\n" +
            "   \"routes\" : [\n" +
            "      {\n" +
            "         \"bounds\" : {\n" +
            "            \"northeast\" : {\n" +
            "               \"lat\" : 21.0101462,\n" +
            "               \"lng\" : 105.8026046\n" +
            "            },\n" +
            "            \"southwest\" : {\n" +
            "               \"lat\" : 20.9692413,\n" +
            "               \"lng\" : 105.7735725\n" +
            "            }\n" +
            "         },\n" +
            "         \"copyrights\" : \"Map data ©2018 Google\",\n" +
            "         \"legs\" : [\n" +
            "            {\n" +
            "               \"distance\" : {\n" +
            "                  \"text\" : \"6.7 km\",\n" +
            "                  \"value\" : 6675\n" +
            "               },\n" +
            "               \"duration\" : {\n" +
            "                  \"text\" : \"18 mins\",\n" +
            "                  \"value\" : 1077\n" +
            "               },\n" +
            "               \"end_address\" : \"118, Quang Trung Street, Ha Dong District, P. Quang Trung, Hà Đông, Hà Nội, Vietnam\",\n" +
            "               \"end_location\" : {\n" +
            "                  \"lat\" : 20.9692413,\n" +
            "                  \"lng\" : 105.7735725\n" +
            "               },\n" +
            "               \"start_address\" : \"Trung Hoà, Cầu Giấy, Hanoi, Vietnam\",\n" +
            "               \"start_location\" : {\n" +
            "                  \"lat\" : 21.0101462,\n" +
            "                  \"lng\" : 105.798849\n" +
            "               },\n" +
            "               \"steps\" : [\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.4 km\",\n" +
            "                        \"value\" : 404\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 67\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 21.0078721,\n" +
            "                        \"lng\" : 105.7958186\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Head \\u003cb\\u003esouthwest\\u003c/b\\u003e on \\u003cb\\u003eTrần Duy Hưng\\u003c/b\\u003e toward \\u003cb\\u003eNgõ 20 Nguyễn Chánh\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by ATM VCB (on the right)\\u003c/div\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"mpf_CyyvdSZb@h@r@^f@b@l@~@zAd@p@p@z@RZpChE\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 21.0101462,\n" +
            "                        \"lng\" : 105.798849\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.3 km\",\n" +
            "                        \"value\" : 257\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 59\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 21.0061603,\n" +
            "                        \"lng\" : 105.797082\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e at VNPT Vinaphone onto \\u003cb\\u003eHoàng Minh Giám\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"ebf_C{fvdSFLLRl@c@n@a@fByAj@e@HKFEd@a@RQ@C@GAC?A\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 21.0078721,\n" +
            "                        \"lng\" : 105.7958186\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.5 km\",\n" +
            "                        \"value\" : 540\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"2 mins\",\n" +
            "                        \"value\" : 91\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 21.0023003,\n" +
            "                        \"lng\" : 105.800237\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Slight \\u003cb\\u003eright\\u003c/b\\u003e at Tập Đoàn Quốc Tế Maz to stay on \\u003cb\\u003eHoàng Minh Giám\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Thiết kế logo tại Hà Nội | Maz.ID (on the right)\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-slight-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"owe_CwnvdSJIj@e@bAw@NMnG{EfBsA~FqE\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 21.0061603,\n" +
            "                        \"lng\" : 105.797082\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.4 km\",\n" +
            "                        \"value\" : 444\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"2 mins\",\n" +
            "                        \"value\" : 97\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 20.999634,\n" +
            "                        \"lng\" : 105.7970873\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e at Chuyên Ga Gối Happiness, Pyeoda, Everon onto \\u003cb\\u003eĐường Lê Văn Lương\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Giấy dán kính mờ An Bình (on the left in 300&nbsp;m)\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"k_e_CobwdSbA|AV^NPPPRRZVr@n@XTZVRNNPNNJJJLJPZd@|@rA\\\\j@JR`@p@\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 21.0023003,\n" +
            "                        \"lng\" : 105.800237\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"1.1 km\",\n" +
            "                        \"value\" : 1067\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"3 mins\",\n" +
            "                        \"value\" : 163\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 20.9919663,\n" +
            "                        \"lng\" : 105.802567\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e onto \\u003cb\\u003eKhuất Duy Tiến\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Cơ Sở Rèm Màn Phương (on the right)\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"und_CynvdSr@bAXSr@k@JITSXQXSd@[RMvA_AJIr@i@tB{ArBeBPMh@a@d@[~@o@`BiA~@o@l@c@t@g@`EaDr@g@b@[jBsAXQ^Y\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 20.999634,\n" +
            "                        \"lng\" : 105.7970873\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"90 m\",\n" +
            "                        \"value\" : 90\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 14\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 20.991185,\n" +
            "                        \"lng\" : 105.8024839\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Slight \\u003cb\\u003eright\\u003c/b\\u003e at Công Ty TNHH In Gia Hưng toward \\u003cb\\u003eNguyễn Trãi\\u003c/b\\u003e/\\u003cb\\u003eQL6\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-slight-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"y~b_CaqwdSJ@JCNAPALAN@NBH@JBJDDDD?F@\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 20.9919663,\n" +
            "                        \"lng\" : 105.802567\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"3.9 km\",\n" +
            "                        \"value\" : 3873\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"10 mins\",\n" +
            "                        \"value\" : 586\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 20.9692413,\n" +
            "                        \"lng\" : 105.7735725\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eNguyễn Trãi\\u003c/b\\u003e/\\u003cb\\u003eQL6\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eContinue to follow QL6\\u003c/div\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by Highlands Coffee (on the right in 2.2&nbsp;km)\\u003c/div\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eDestination will be on the right\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"{yb_CopwdSRXlAfB|AdCBDBBFJvAzBLTp@dAFDFDF@hD~FtAvBbDhFfBpC|@nApBzCtBlDpArB~AbCvHtKpCrDdCbD~B|CpEjGhBdCRXtBpCbBdClA~AJLJHJHJJNP`@p@pC|DjBtC`@d@nC`EZb@Xb@lAfB\\\\d@DHbCfEJLdEhG`ArAd@n@v@|@bBvBHJ`CzCjBbCjBfC\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 20.991185,\n" +
            "                        \"lng\" : 105.8024839\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"DRIVING\"\n" +
            "                  }\n" +
            "               ],\n" +
            "               \"traffic_speed_entry\" : [],\n" +
            "               \"via_waypoint\" : []\n" +
            "            }\n" +
            "         ],\n" +
            "         \"overview_polyline\" : {\n" +
            "            \"points\" : \"mpf_CyyvdShClDdBlCdAvAxCvELRl@c@n@a@fByAt@q@l@g@TU?KJKnB}A~GiFfJeHzA|BpAnA|BlBv@z@bCvDl@dAr@bAXS~@u@n@e@jD}B~@s@hFaEz@o@dBkAdGeEtFiEnCoBx@k@VA`@C\\\\?XDVHJDF@RXjDlFFH~CbFNJF@hD~FxF`JfBpC|@nApBzCtBlDpDvFvHtKpCrDdG`IdM|PpDdFVVVTp@bA|FrI`@d@nC`Et@fAjBlChCpEpEvGfBbCzCtDjCfDvEjG\"\n" +
            "         },\n" +
            "         \"summary\" : \"QL6\",\n" +
            "         \"warnings\" : [],\n" +
            "         \"waypoint_order\" : []\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}";
}
