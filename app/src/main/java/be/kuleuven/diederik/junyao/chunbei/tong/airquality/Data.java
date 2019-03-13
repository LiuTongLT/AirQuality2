package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Data implements Serializable{

    ArrayList measurements = new ArrayList<Measurement>();
    ArrayList sensors = new ArrayList<Sensor>();

    public Data(){}

    public void addMeasurement(Measurement measurement)throws AlreadyAddedException{

        if(measurements.contains(measurement)){
            throw new AlreadyAddedException();}
        else{
            measurements.add(measurement);}
    }

    public Measurement getMeasurementByDate(Date date)throws NotInListException,EmptyListException{
        if(measurements.isEmpty()){throw new EmptyListException();}
        else{
            Iterator<Measurement> it = measurements.iterator();
            while(it.hasNext()){
                Measurement currentMeasurement = it.next();
                if (currentMeasurement.getDate().equals(date)){return currentMeasurement;}
            }
            throw new NotInListException();
        }
    }
    public ArrayList getMeasurementsByLocation(String location)throws EmptyListException{
        if(measurements.isEmpty()){throw new EmptyListException();}
        else{
            ArrayList measurementsByLocation = new ArrayList<Measurement>();
            Iterator<Measurement> it = measurements.iterator();
            while(it.hasNext()){
                Measurement currentMeasurement = it.next();
                if(currentMeasurement.getLocation().equals(location)){measurementsByLocation.add(currentMeasurement);}
            }
            return measurementsByLocation;
        }
    }

    public ArrayList getMeasurementsByTimeInterval(String location, Date beginDate, Date endDate)throws EmptyListException{
        ArrayList measurementsByTimeInterval = new ArrayList<Measurement>();
        Iterator<Measurement> it = measurements.iterator();
        while(it.hasNext()){
            Measurement currentMeasurement = it.next();
            if(currentMeasurement.getLocation().equals(location) && currentMeasurement.getDate().after(beginDate) && currentMeasurement.getDate().before(endDate)){
                measurementsByTimeInterval.add(currentMeasurement);}
        }
        if(measurementsByTimeInterval.isEmpty()){throw new EmptyListException();}
        else{return measurementsByTimeInterval;}
    }

    public void addSensor(Sensor sensor)throws AlreadyAddedException{
        if(sensors.contains(sensor)){throw new AlreadyAddedException();}
        else{sensors.add(sensor);}
    }

    public ArrayList<Sensor> getSensors(){return sensors;}
}
