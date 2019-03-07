package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Data implements Serializable{

    ArrayList measurements = new ArrayList<Measurement>();
    ArrayList users = new ArrayList<User>();
    ArrayList sensors = new ArrayList<Sensor>();

    public Data(){}

    public void addUser(User user)throws AlreadyAddedException{
        try{
            getUserByMailAddress(user.getMailAddress(),false);
            throw new AlreadyAddedException();
        }
        catch (NotInListException N){
            users.add(user);
        }
    }

    public User getUserByMailAddress(String mailAddress, boolean remove)throws NotInListException{
        Iterator<User> it = users.iterator();
        while(it.hasNext()){
            User currentUser = it.next();
            if(currentUser.getMailAddress().equals(mailAddress)){
                if(remove){
                    it.remove();}
                return currentUser;}
        }
        throw new NotInListException();
    }

    public void addMeasurement(Measurement measurement)throws AlreadyAddedException{

        if(measurements.contains(measurement)){
            throw new AlreadyAddedException();}
        else{
            measurements.add(measurement);}
    }

    public Measurement getMeasurementByDate(Date date)throws NotInListException,EmptyListException{
        if(measurements.isEmpty()){
            throw new EmptyListException();}
        else{
            Iterator<Measurement> it = measurements.iterator();
            while(it.hasNext()){
                Measurement currentMeasurement = it.next();
                if (currentMeasurement.getDate().equals(date)){
                    return currentMeasurement;}
            }
            throw new NotInListException();
        }
    }

    public ArrayList getMeasurementsByTimeInterval(int sensorId, Date beginDate, Date endDate)throws EmptyListException{
        ArrayList measurementsByTimeInterval = new ArrayList<Measurement>();
        Iterator<Measurement> it = measurements.iterator();
        while(it.hasNext()){
            Measurement currentMeasurement = it.next();
            if(currentMeasurement.getSensorId()==sensorId && currentMeasurement.getDate().after(beginDate) && currentMeasurement.getDate().before(endDate)){
                measurementsByTimeInterval.add(currentMeasurement);}
        }
        if(measurementsByTimeInterval.isEmpty()){
            throw new EmptyListException();}
        else{
            return measurementsByTimeInterval;}
    }

    public void addSensor(Sensor sensor)throws AlreadyAddedException{
        if(sensors.contains(sensor)){
            throw new AlreadyAddedException();}
        else{
            sensors.add(sensor);}
    }
}
