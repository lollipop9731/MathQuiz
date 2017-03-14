package com.example.l.mathquiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lorenz on 28.02.2017.
 */

public class Rewards {
    int id;
    String goal;
    long datetime;

    //Constructors

    public Rewards(){

    }

    public Rewards(String goal, int datetime){
        this.goal = goal;
        this.datetime = datetime;
    }

    // Setters
    public void setGoal(String goal){
        this.goal = goal;
    }

    public void setDatetime(long datetime){
        this.datetime = datetime;
    }

    // Getters
    public String getGoal(){
        return this.goal;
    }

    public long getDatetime(){
        return this.datetime;
    }

    public String convertDate(long datetime){
        Date date = new Date(datetime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return simpleDateFormat.format(date);
    }

    @Override
    public String toString(){
        String rewards = "Das Feld" + getGoal() + "wurde eingelöst " + "am " + convertDate(getDatetime());
        return rewards;
    }
}
