package com.example.myapplication.EX3Package;

public class employee {
    private String id;
    private String name;
    public employee(){}
    public employee(String id, String name){
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double salary(){
        return 0;
    }

    public String toString(){
        return super.toString() + "->";
    }

}
