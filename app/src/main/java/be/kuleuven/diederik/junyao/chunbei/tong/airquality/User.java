package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import java.io.Serializable;

public class User implements Serializable{

    String firstName;
    String lastName;
    String mailAddress;
    String password;

    public User (String firstName, String lastName, String mailAddress, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.mailAddress=mailAddress;
        this.password=password;
    }

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getMailAddress() {return mailAddress;}
    public void setMailAddress(String mailAddress) {this.mailAddress = mailAddress;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

}
