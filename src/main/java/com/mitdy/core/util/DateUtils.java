package com.mitdy.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    public static String formatDateTime(Date date) {
        if (date != null) {
            return datetimeFormatter.format(date);
        } else {
            return "";
        }
    }

    public static Date parseAsData(String text) {
        try {
            return dateFormatter.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseAsDataTime(String text) {
        try {
            return datetimeFormatter.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

}
