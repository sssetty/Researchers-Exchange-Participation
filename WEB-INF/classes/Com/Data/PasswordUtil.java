/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Com.Data;
import Com.Model.User;
import java.security.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
/**
 *
 * @author sadhana
 */
public class PasswordUtil {
    public static  String hashPassword(String password)
            throws NoSuchAlgorithmException{
        MessageDigest md=MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray=md.digest();
        StringBuilder sb=new StringBuilder(mdArray.length*2);
        for (byte b:mdArray){
            int v=b & 0xff;
            if(v<16){
                sb.append('0');
                }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    public static String getSalt(){
        Random r=new SecureRandom();
        byte[] saltBytes= new byte[32];
        r.nextBytes(saltBytes);
        return Base64.encodeBase64String(saltBytes);
        }
    public static String[] hashandsaltPassword(String password) throws NoSuchAlgorithmException{
        String[] has=new String[2];
        String salt=getSalt();
        has[0]=salt;
        String h=password+salt;
       String hp=hashPassword(h);
                 has[1]=hp;
                 return has;
					
        
    }
    
  
}
