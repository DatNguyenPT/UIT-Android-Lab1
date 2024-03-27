package com.example.myapplication.EX3Package;

public class employeeFullTime extends employee{
    @Override
    public double salary(){
        return 500.0;
    }
    @Override
    public String toString(){
        return super.toString() + "->";
    }
}
