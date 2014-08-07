package com.example.group1.assignment2;

public class ObjectEventData {

    private Integer id;
    private String timestamp;
    private Integer x;
    private Integer y;
    private Integer z;


    public ObjectEventData(){}

    public ObjectEventData(Integer id, String time, Integer x, Integer y, Integer z) {
        super();
        this.id = id;
        this.timestamp = time;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //getters & setters

    @Override
    public String toString() {
        return "ID: \t" + id + "\tTime: \t" + timestamp + "\tX Value: " + x + "\tY Value: " + y + "\tZ Value: " + z;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTimeStamp(String time) {
        this.timestamp = time;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTimeStamp() {
        return this.timestamp;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public Integer getZ() {
        return this.z;
    }

}