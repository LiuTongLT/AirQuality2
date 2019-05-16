package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

public class InfoWindowData {
    private String location;
    private String pmValue;
    private String coValue;
    private Sensor sensor;
    private String date;

    public Sensor getSensor() {return sensor;}
    public void setSensor(Sensor sensor) {this.sensor = sensor;}
    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}
    public String getPmValue() {return pmValue;}
    public void setPmValue(String pmValue) {this.pmValue = pmValue;}
    public String getCoValue() {return coValue;}
    public void setCoValue(String coValue) {this.coValue = coValue;}
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}
}
