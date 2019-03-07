package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import java.io.Serializable;

public class Sensor implements Serializable{

    int sensorId;
    double xcoordinate;
    double ycoordinate;
    String location;

    public Sensor(int sensorId, double xcoordinate, double ycoordinate, String location, int houseNumber) {
        this.sensorId = sensorId;
        this.xcoordinate = xcoordinate;
        this.ycoordinate = ycoordinate;
        this.location = location;
    }

    public int getSensorId() {return sensorId;}
    public void setSensorId(int sensorId) {this.sensorId = sensorId;}
    public double getXcoordinate() {return xcoordinate;}
    public void setXcoordinate(double xcoordinate) {this.xcoordinate = xcoordinate;}
    public double getYcoordinate() {return ycoordinate;}
    public void setYcoordinate(double ycoordinate) {this.ycoordinate = ycoordinate;}
    public String getLocation() {return location;}
    public void setStreetName(String location) {this.location = location;}
}
