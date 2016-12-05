package Com.Data;

import java.io.UnsupportedEncodingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

//import java.sql.ResultSet;
//import java.sql.SQLException;
/*import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
*/

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;


public class EmailDB {


	public static void emailRecommendTrigger(String senderMail,  String friendMail){
               final String username = "balohalo76@gmail.com";
		final String password = "Sail1994!";
		String to = friendMail ;
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			 InternetAddress me = new InternetAddress("balohalo76@gmail.com");
		        try {
					me.setPersonal("Research Exchange Participations");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
                        Address toAddress= new InternetAddress(friendMail);
       
		        message.setFrom(me);
			//for (int i = 0; i < to.length; i++) {
			message.setRecipient(Message.RecipientType.TO,toAddress);
			
			message.setSubject("Participate in my Research");
                        message.setText("Please take part in my Research\n"+"Click on the link below to take part\n"+"http://localhost:8080/Sadhana_Assignment4/UserController?action=bonus&semail="+senderMail+"&femail="+friendMail);

                        //message.setText("Please take part in my Research\n"+"Click on the link below to take part\n"+"http://project-sssetty.rhcloud.com//Sadhana_Assignment4/UserController?action=bonus&semail="+senderMail+"&femail="+friendMail);
			//message.setText("Dear "+ email.split("\\@")[0]  + ",\n" 
			//		+ "\nTo activate your account click on the link below.\n\n"
			//		+"Message:  "+messageTxt
			//		+ "\n\nRegards,\n" + name + "");

			Transport.send(message);
        } catch (MessagingException e) {
			throw new RuntimeException(e);
		}
        }
		
			//message.setText("Hello!Please participate in my Research/n"+"Click on the link below to take part"+"http://localhost:8080/RohithSagar_Assignment3/StudyController?action=bonus&email="+senderMail);

			
	
	
	public static void emailnewuser(String name, String email,String id){
         final String username = "balohalo76@gmail.com";
		final String password = "Sail1994!";
		String[] to = { email };
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			 InternetAddress me = new InternetAddress("balohalo76@gmail.com");
		        try {
					me.setPersonal("Research Exchange Participations");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
                        Address toAddress= new InternetAddress(email);
       
		        message.setFrom(me);
			//for (int i = 0; i < to.length; i++) {
			message.setRecipient(Message.RecipientType.TO,toAddress);
			
			message.setSubject("Acount Activation");
                        
                        //message.setText("Welcome to Research Exchange Participations\n"+"Click on the link below to activate your account\n"+"http://project-sssetty.rhcloud.com//Sadhana_Assignment4/UserController?action=activate&name="+name+"&token="+id);
                        message.setText("Welcome to Research Exchange Participations\n"+"Click on the link below to activate your account\n"+"http://localhost:8080/Sadhana_Assignment4/UserController?action=activate&name="+name+"&token="+id);
			//message.setText("Dear "+ email.split("\\@")[0]  + ",\n" 
			//		+ "\nTo activate your account click on the link below.\n\n"
			//		+"Message:  "+messageTxt
			//		+ "\n\nRegards,\n" + name + "");

			Transport.send(message);
        } catch (MessagingException e) {
			throw new RuntimeException(e);
		}
        }
       
	
	
	public static void emailContactTrigger(String name, String friendMail, String messageTxt){
		final String username = "balohalo76@gmail.com";
		final String password = "Sail1994!";
		String[] to = { friendMail };
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			 InternetAddress me = new InternetAddress("balohalo76@gmail.com");
		        try {
					me.setPersonal(name);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		        message.setFrom(me);
			for (int i = 0; i < to.length; i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
			}
			message.setSubject("New Contact");
			message.setText("Dear "+ friendMail.split("\\@")[0]  + ",\n" 
					+ "\nYou have been added as a contact for "+name+".\n\n"
					+"Message:  "+messageTxt
					+ "\n\nRegards,\n" + name + "");

			Transport.send(message);


		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
        public static void emailreset(String email){
         final String username = "balohalo76@gmail.com";
		final String password = "Sail1994!";
		String to = email ;
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			 InternetAddress me = new InternetAddress("balohalo76@gmail.com");
		        try {
					me.setPersonal("Research Exchange Participations");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
                        Address toAddress= new InternetAddress(email);
       
		        message.setFrom(me);
			//for (int i = 0; i < to.length; i++) {
			message.setRecipient(Message.RecipientType.TO,toAddress);
			
			message.setSubject("Password Reset");
                       // message.setText("Click on the link below to reset your password\n"+"http://project-sssetty.rhcloud.com//Sadhana_Assignment4/UserController?action=npass&email="+to);
                        message.setText("Click on the link below to reset your password\n"+"http://localhost:8080/Sadhana_Assignment4/UserController?action=npass&email="+to);
			
//message.setText("Dear "+ email.split("\\@")[0]  + ",\n" 
			//		+ "\nTo activate your account click on the link below.\n\n"
			//		+"Message:  "+messageTxt
			//		+ "\n\nRegards,\n" + name + "");

			Transport.send(message);
        } catch (MessagingException e) {
			throw new RuntimeException(e);
		}
        }
}
