/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Com.Model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author SAI KUMAR
 */
public class Temp  implements Serializable {

    private String name, email,Password,token;
    private Timestamp time;
    

    public Temp() {
        this.name = "";
        this.email = "";
        this.Password="";
        this.token="";
        
        
    }
    
     public Temp(String Name,String Email,String Password,String token,Timestamp time) {
        this.name = Name;
        this.email = Email;
        this.Password=Password;
        this.token=token;
        this.time=time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token= token;
}
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
