package com.example.myapplication.EX3Package;

public class Student {
    private String name;
    private String classID;
    private String id;

    public Student(){}
    public Student(String name, String classID, String id) {
        this.name = name;
        this.classID = classID;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
