package Com.Model;

import java.io.Serializable;


public class User implements Serializable{
	private String salt;
	private String name;
	private String email;
	private String userType;
	private String password;
	private String confirmPassword;
	private int numCoins;
	private int numPostedStudies;
	private int numParticipation;

    public User(String name, String email, String participant, String password, String cPassword, int i, int i0, String id,String salt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password =password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public int getNumCoins() {
		return numCoins;
	}
	public void setNumCoins(int numCoins) {
		this.numCoins = numCoins;
	}
	public int getNumPostedStudies() {
		return numPostedStudies;
	}
	public void setNumPostedStudies(int numPostedStudies) {
		this.numPostedStudies = numPostedStudies;
	}
	public int getNumParticipation() {
		return numParticipation;
	}
	public void setNumParticipation(int numParticipation) {
		this.numParticipation = numParticipation;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public User(String name, String email, String userType, String password, String confirmPassword, int numCoins,
			int numPostedStudies, int numParticipation,String salt) {
		super();
		this.name = name;
		this.email = email;
		this.userType = userType;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.numCoins = numCoins;
		this.numPostedStudies = numPostedStudies;
		this.numParticipation = numParticipation;
                this.salt=salt;
	}
	
	public User(String name, String email, String userType) {
		super();
		this.name = name;
		this.email = email;
		this.userType = userType;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", userType=" + userType + ", numCoins=" + numCoins
				+ ", numPostedStudies=" + numPostedStudies + ", numParticipation=" + numParticipation + "]";
	}

    public String getToken() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	

}
