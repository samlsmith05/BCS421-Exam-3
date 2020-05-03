package com.example.bcs421exam3;

public class Assignment {

    private String name;
    private int grade;

    public String getName(){return name; }
    public void setName(String name){this.name = name;}

    public int getGrade(){return grade; }
    public void setGrade(int grade){this.grade = grade;}

    public Assignment(String name, int grade){
        this.name = name;
        this.grade = grade;
    }
}
