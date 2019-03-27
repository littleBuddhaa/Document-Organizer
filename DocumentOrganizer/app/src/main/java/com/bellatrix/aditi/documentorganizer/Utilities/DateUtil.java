package com.bellatrix.aditi.documentorganizer.Utilities;

/**
 * Created by Aditi on 27-03-2019.
 */

public class DateUtil {

    private int year,month,day;

    public DateUtil()
    {
        this.year = 0;
        this.month = 0;
        this.day = 0;
    }

    public DateUtil(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}