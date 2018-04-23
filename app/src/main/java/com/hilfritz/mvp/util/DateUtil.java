package com.hilfritz.mvp.util;

import android.content.Context;
import android.util.Log;

import com.hilfritz.mvp.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hilfritz Camallere on 23/3/16.
 */
public class DateUtil {
    public static final String TAG = "DateUtil";

    public static final String dateFormatA = "MM/dd/yyyy";

    public static final String DATEFORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DISPLAY_DATE_FORMAT3 = "dd MMMM ''yy "; //01 JAN '16
    public static final String DISPLAY_DATE_FORMAT = "dd MMM ''yy "; //01 Jan '16
    public static final String DISPLAY_DATE_FORMAT2 = "EEE, dd MMM ''yy "; //01 Jan '16
    public static final String DISPLAY_DATE_FORMAT4 = "EEE, dd MMM "; //Tue, 01 Jan '16
    public static final String DISPLAY_DATE_FORMAT5 = "hh:mma "; //09:59AM

    public static final String DISPLAY_DATE_FORMAT6 = "MMM ''yy "; //JAN '16
    public static final String DISPLAY_DATE_FORMAT7 = "hh:mmaa, dd MMM ''yy  "; //18:59, 11 Aug'15
    public static final String DISPLAY_DATE_FORMAT8 = "dd";
    public static final String DISPLAY_DATE_FORMAT9 = "MMM"; //JAN

    /**
     * transforms the Short names of the month to uppercase
     * @param context
     * @param dateStr String dateString - this string parameter must have the Short Names of the months.
     * @return
     */
    public static String formatMonthNameToUppercase(Context context, String dateStr) {
        //MAKE SURE THE MONTH IS UPPERCASE
        //DOING IT MANUALLY
        String retVal = "";

        if (StringUtil.isStringEmpty(dateStr)){
            return retVal;
        }

        String[] monthArray= context.getResources().getStringArray(R.array.label_month_array);
        int length = monthArray.length;
        int index = 0;
        for (int j = 0; j < length; j++) {
            if (dateStr.contains(monthArray[j])){
                index = j;
                break;
            }
        }
        retVal = dateStr.replace(monthArray[index], monthArray[index].toUpperCase());
        return retVal;
    }

    /**
     *
     * @param timeZone DateTimeZone timezone of the server
     * @param dateTimeToParse String date time string in UTC format yyyy-MM-dd'T'HH:mm:ss'Z' (UTC format only)
     * @return
     */
    public static String formatDate(String dateDisplayFormat, DateTimeZone timeZone, String dateTimeToParse){
        String retVal = "";
        try {
            DateTimeFormatter dateFormat = DateTimeFormat.forPattern(dateDisplayFormat).withZone(timeZone);
            DateTime dateTime = DateTime.parse(dateTimeToParse).withZone(DateTimeZone.UTC);
            dateTime = dateTime.withZone(timeZone);
            retVal = dateFormat.print(dateTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     *
     * @param timezone String ex. "Asia/Singapore"
     * @param dateTime DateTime the DateTime object to format
     * @param dateFormat String ex yyyy-mm-dd
     * @return String the formated date
     */
    public static String formatDate(String timezone, DateTime dateTime, String dateFormat){
        DateTimeZone zone = DateTimeZone.forID(timezone);
        DateTime zoned = dateTime.withZone(zone);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormat).withZone(zone);
        return fmt.print(zoned);
    }
    /**
     *
     * @param timeZone DateTimeZone ex. "Asia/Singapore"
     * @param dateTime DateTime the DateTime object to format
     * @param dateFormat String ex yyyy-mm-dd
     * @return String the formated date
     */
    public static String formatDate(DateTimeZone timeZone, DateTime dateTime, String dateFormat){
        DateTime zoned = dateTime.withZone(timeZone);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormat).withZone(timeZone);
        return fmt.print(zoned);
    }
    /**
     *
     * @param dateTime DateTime the DateTime object to format
     * @param dateFormat String ex yyyy-mm-dd
     * @return String the formated date
     */
    public static String formatDate (DateTime dateTime, String dateFormat){
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormat);
        return fmt.print(dateTime);
    }

    /**
     *
     * @param dateTime DateTime the DateTime object to format
     * @param dateFormat String "yyyy-MM-dd'T'HH:mm:ss'Z'"
     * @return String the formated date
     */
    public static String formatDateToUTC(DateTime dateTime, String dateFormat){;
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormat).withZoneUTC();
        return fmt.print(dateTime);
    }
    /**
     * todo
     * something wrong here, removed withZoneUTC
     * @param dateTime DateTime the DateTime object to format
     * @return String the formated date "yyyy-MM-dd'T'HH:mm:ss'Z'"
     */
    @Deprecated
    public static String formatDateToUTCZ(DateTime dateTime){;
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DateUtil.DATEFORMAT_UTC).withZoneUTC();
        return fmt.print(dateTime);
    }

    /**
     *
     * @param fromUtcDateToParse String UTC format
     * @param fromDateFormat
     * @param toDateFormat
     * @param fromTimeZone
     * @return
     */
    public static String convertToFormat2(String fromUtcDateToParse, String fromDateFormat, String toDateFormat, String fromTimeZone) {

        SimpleDateFormat fromFormatter = new SimpleDateFormat(fromDateFormat);
        SimpleDateFormat toFormatter = new SimpleDateFormat(toDateFormat);
        String currentDate = "";
        Date date;

        try {
            //ADD TIMEZONE TO FROM FORMATTER
            fromFormatter.setTimeZone(TimeZone.getTimeZone(fromTimeZone));
            date = fromFormatter.parse(fromUtcDateToParse);

            currentDate = fromFormatter.format(date);
            toFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            currentDate = toFormatter.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return currentDate;
    }



    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;



    /**
     *
     * @param time long timestamp from Date or DateTime
     * @param ctx
     * @param now System.currentTimeMillis()
     * @return
     */
    public static String getTimeAgo(long time, Context ctx, long now) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "A minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "An hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }


    public static DateTime createDateTimeObject(int day, int month, int year, String timezoneStr){
        //get the timezone of the location, here we just explicitly set to Asia/Singapore
        DateTimeZone timezone = DateTimeZone.forID(timezoneStr);
        //create the datetiime object
        DateTime dateTime = new DateTime(year,month,day,0,0,timezone);
        return dateTime;
    }
    public static DateTime createDateTimeObject(int day, int month, int year){
        //get the timezone of the location, here we just explicitly set to Asia/Singapore
        //create the datetiime object
        DateTime dateTime = new DateTime(year,month,day,0,0);
        return dateTime;
    }
    public static DateTime createDateTimeObject2(int day, int month, int year, String timezoneStr){
        //get the timezone of the location, here we just explicitly set to Asia/Singapore
        DateTimeZone timezone = DateTimeZone.forID(timezoneStr);
        DateTime now = new DateTime();
        now = now.withZone(timezone);
        //create the datetiime object
        DateTime dateTime = new DateTime(year,month,day,now.getHourOfDay(), now.getMinuteOfHour());
        return dateTime.withZone(timezone);
    }

    /**
     *
     * @param dateUtcStr String must be in UTC format see {@link DateUtil#DATEFORMAT_UTC}

     * @return
     */
    public static DateTime parseUtcDate(String dateUtcStr){
        DateTime retVal = null;
        retVal = DateTime.parse(dateUtcStr).withZone(DateTimeZone.UTC);
        return retVal;
    }

    public static DateTime parseUtcDateWithTimezone(String dateUtcStr, String timezone){
        DateTime retVal = null;
        retVal = DateUtil.parseUtcDateWithTimezone(dateUtcStr, DateTimeZone.forID(timezone));
        return retVal;
    }
    public static DateTime parseUtcDateWithTimezone(String dateUtcStr, DateTimeZone dateTimeZone){
        DateTime retVal = null;
        retVal = DateTime.parse(dateUtcStr).withZone(DateTimeZone.UTC);
        retVal = retVal.withZone(dateTimeZone);
        return retVal;
    }


    public static DateTime parseDate(String dateUtcStr, DateTimeZone dateTimeZone){
        DateTime retVal = null;
        retVal = DateTime.parse(dateUtcStr).withZone(dateTimeZone);
        return retVal;
    }

    public static final ArrayList<DateTime> getAllDateTimeInMonth(int month, int year, String timezoneID){
        Log.d(TAG, "getAllDateTimeInMonth: month:"+month);

        DateTimeZone dateTimeZone = DateTimeZone.forID(timezoneID);
        DateTime dateTime = new DateTime(year, month, 1,0, 0, dateTimeZone);

        LocalDate localDate = new LocalDate(dateTime);

        Log.d(TAG, "getAllDateTimeInMonth: month:"+month+" year:"+year);


        ArrayList<DateTime> daysInMonthLabels = new ArrayList<DateTime>();
        LocalDate firstDay = localDate.withDayOfMonth(1);
        LocalDate nextMonthFirstDay = firstDay.plusMonths(1);
        while (firstDay.isBefore(nextMonthFirstDay)) {
            firstDay = firstDay.plusDays(1);
            daysInMonthLabels.add(firstDay.toDateTimeAtStartOfDay());
        }
        return daysInMonthLabels;
    }

}
