package com.eliteinfotech.vewash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String getTodayDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return  df.format(c);
    }
    public static String convertToString(String dates,String dateFormat){

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = format.parse(dates);
            SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
            simpleDateFormat.applyPattern(dateFormat);
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            return e.getLocalizedMessage();
        }
    }
}
