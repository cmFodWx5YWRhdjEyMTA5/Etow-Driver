package com.app.etow.utils;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.common.ParameterException;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {

    private static final String DEFAULT_FORMAT_DATE = "MM/dd/yyyy";
    private static final String DEFAULT_FORMAT_DATE_2 = "EEEE dd MMM yyyy";
    private static final String DEFAULT_FORMAT_DATE_3 = "hh:mm a";
    private static final String DEFAULT_FORMAT_DATE_4 = "yyyy-MM-dd HH:mm:ss"; //2018-08-04 10:10:32
    private static final String DEFAULT_FORMAT_DATE_5 = "hh:mm a, dd-MMM-yyyy";
    private static final String DEFAULT_FORMAT_DATE_6 = "EEEE dd MMM yyyy hh:mm a";

    public static boolean isDateBefore(Date currentDate, Date pivotDate) {
        if (currentDate == null || pivotDate == null) {
            throw new ParameterException("date can not be null");
        }
        return currentDate.compareTo(pivotDate) < 0;
    }

    public static boolean isDateEqual(Date currentDate, Date pivotDate) {
        if (currentDate == null || pivotDate == null) {
            throw new ParameterException("date can not be null");
        }
        return currentDate.compareTo(pivotDate) == 0;
    }

    public static boolean isDateAfter(Date currentDate, Date pivotDate) {
        if (currentDate == null || pivotDate == null) {
            throw new ParameterException("date can not be null");
        }
        return currentDate.compareTo(pivotDate) > 0;
    }

    public static boolean isDateBefore(Date currentDate, long pivotDate) {
        if (currentDate == null) {
            throw new ParameterException("date can not be null");
        }
        return currentDate.getTime() < pivotDate;
    }

    public static boolean isDateEqual(Date currentDate, long pivotDate) {
        if (currentDate == null) {
            throw new ParameterException("date can not be null");
        }
        return currentDate.getTime() == pivotDate;
    }

    public static boolean isDateAfter(Date currentDate, long pivotDate) {
        if (currentDate == null) {
            throw new ParameterException("date can not be null");
        }
        return currentDate.getTime() > pivotDate;
    }

    public static int convertMonthToNumber(String monthName) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("MMM").parse(monthName));
            return cal.get(Calendar.MONTH) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDateToday() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
        return df.format(c.getTime());
    }

    public static String getCurrentTimeStamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String result = tsLong.toString();
        return result;
    }

    public static String getCurrentTimeStampGTM() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss.SS");
        fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        return fmt.format(timestamp);
    }

    public static String getTimeCurrent() {
        int hours = new Time(System.currentTimeMillis()).getHours();
        int minutes = new Time(System.currentTimeMillis()).getMinutes();
        String strHours;
        String strMinutes;
        if (hours < 10) strHours = "0" + hours;
        else strHours = "" + hours;

        if (minutes < 10) strMinutes = "0" + minutes;
        else strMinutes = "" + minutes;
        return strHours + ":" + strMinutes;
    }

    public static String getMonth(int number) {
        String month = "";
        switch (number) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
        }
        return month;
    }

    public static String convertDateToTimeStamp(String strDate) {
        String result = "";
        if (strDate != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT_DATE_4);
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date date = format.parse(strDate);
                Long timestamp = date.getTime() / 1000;
                result = String.valueOf(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String convertTimeStampToFormatDate2(String strTimeStamp) {
        String result = "";
        if (strTimeStamp != null) {
            try {
                Float floatTimestamp = Float.parseFloat(strTimeStamp);
                Long timestamp = (long) (floatTimestamp * 1000);
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_2);
                Date date = (new Date(timestamp));
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String convertTimeStampToFormatDate3(String strTimeStamp) {
        String result = "";
        if (strTimeStamp != null) {
            try {
                Float floatTimestamp = Float.parseFloat(strTimeStamp);
                Long timestamp = (long) (floatTimestamp * 1000);
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_3);
                Date date = (new Date(timestamp));
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String convertTimeStampToDateFormat5(String strTimeStamp) {
        String result = "";
        if (strTimeStamp != null) {
            try {
                Float floatTimestamp = Float.parseFloat(strTimeStamp);
                Long timestamp = (long) (floatTimestamp * 1000);
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_5);
                Date date = (new Date(timestamp));
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String convertTimeStampToDateFormat6(String strTimeStamp) {
        String result = "";
        if (strTimeStamp != null) {
            try {
                Float floatTimestamp = Float.parseFloat(strTimeStamp);
                Long timestamp = (long) (floatTimestamp * 1000);
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_6);
                Date date = (new Date(timestamp));
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
