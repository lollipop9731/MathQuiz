package com.example.l.mathquiz;

/**
 * Created by Lorenz on 22.03.2017.
 */

public class DateTest extends Object {

    int day;
    int month;
    int year;

    public DateTest(){

    }

    /**
     *
     * @param day 1-31
     * @param month 0-11
     * @param year
     */
    public DateTest(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    /**
     *
     * @return month in correct format 1-12
     */
    public int getMonth() {
        return month+1;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toString(){
        return getDay() + "." +getMonth()+"."+getYear();
    }
}
