package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import com.jjoe64.graphview.series.DataPoint;

import java.io.Serializable;
import java.util.Date;

public class Measurement implements Serializable{

    double coValue;
    double pmValue;
    Date date;
    String location;

    public Measurement(double coValue, double pmValue, Date date, String location){
        this.coValue=coValue;
        this.pmValue=pmValue;
        this.date=date;
        this.location=location;
    }

    public double getCoValue() {return coValue;}
    public void setCoValue(double coValue) {this.coValue = coValue;}
    public double getPmValue() {return pmValue;}
    public void setPmValue(double pmValue) {this.pmValue = pmValue;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
    public int getSeconds(){return date.getSeconds();}
    public int getMinutes(){return date.getMinutes();}
    public int getDay(){return date.getDay();}
    public int getMonth(){return date.getMonth();}
    public int getYear(){return date.getYear();}

    public String getLocation() {return location;}
    public void setLocation(int sensorId) {this.location = location;}
}
