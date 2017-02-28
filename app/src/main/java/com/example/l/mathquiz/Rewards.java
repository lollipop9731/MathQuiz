package com.example.l.mathquiz;

/**
 * Created by Lorenz on 28.02.2017.
 */

public class Rewards {
    int id;
    String goal;
    int datetime;

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

    public void setDatetime(int datetime){
        this.datetime = datetime;
    }

    // Getters
    public String getGoal(){
        return this.goal;
    }

    public int getDatetime(){
        return this.datetime;
    }
}
