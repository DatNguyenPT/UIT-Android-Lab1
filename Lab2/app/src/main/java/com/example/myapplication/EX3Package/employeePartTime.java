package com.example.myapplication.EX3Package;

public class employeePartTime extends employee{
    @Override
    public double salary(){
        return 150.0;
    }
    @Override
    public String toString(){
        return "name: " + getName() + ", ID: " + getId() +", role: part time" + ", salary: " + salary();
    }

}
