package com.example.group1.assignment2;

public class Patient {

    private int id;
    private int x;
    private int y;
    private int z;


    public Patient(){}

    public Patient(int id, int x, int y, int z) {
        super();
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //getters & setters

    @Override
    public String toString() {
        return "ID: \t" + id + "\tX Value: " + x + "\tY Value: " + y + "\tZ Value: " + z;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

}