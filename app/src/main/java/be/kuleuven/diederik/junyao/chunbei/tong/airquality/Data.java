package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Data implements Serializable{

    ArrayList measurements = new ArrayList<Measurement>();
    ArrayList users = new ArrayList<User>();
    ArrayList sensors = new ArrayList<Sensor>();

    public Data(){}

    public void addUser()throws AlreadyAddedException{}

    public User getUserByMailAddress(String mailAddress)throws NotInListException{return null;}

    public void addMeasurement()throws AlreadyAddedException{}

    public Measurement getMeasurementByDate(Date date)throws NotInListException{return null;}

    public void addSensor()throws AlreadyAddedException{}
}
