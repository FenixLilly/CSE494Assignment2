package com.example.group1.assignment2;

public class Patient {

    private String name;
    private int ID;
    private int age;
    private String sex;


    public Patient(){}

    public Patient(String name, int ID, int age, String sex) {
        super();
        this.name = name;
        this.ID = ID;
        this.age = age;
        this.sex = sex;
    }

    //getters & setters

    @Override
    public String toString() {
        return "Patient -- Name: " + name + "\tID: " + ID + "\tAge: " + age + "\tSex: " + sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.ID;
    }

    public int getAge() {
        return this.age;
    }

    public String getSex() {
        return this.sex;
    }

}