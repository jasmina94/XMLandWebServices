package com.ftn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JELENA on 16.6.2017.
 */
public class MyDatatypeConverter {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseDate(String value) {
        try{
            return dateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String printDate(Date value) {
        if(value != null) {
            return dateFormat.format(value);
        } else {
            return null;
        }
    }
}
