package Com.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;

import Com.Model.*;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;


public class UserDB {


	
	 public static String[] selectUser(String email) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        String query = "select * FROM user WHERE Username = ?";
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setString(1, email);
	            
	            rs= ps.executeQuery();
	            while (rs.next())
	            {
                        String[] vals=new String[2];
                	vals[0]=rs.getString("salt");
                        vals[1]=rs.getString("Password");
                       // System.out.println("Salt is : " + vals[0]);
                        //System.out.println("Hashed password is : " + vals[1]);
                        return vals;
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	           return null;
	        } finally {
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
			return null;
         }


	
	
	  public static User getUser(String email) {
	         ConnectionPool pool = ConnectionPool.getInstance();
                Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        String query = "SELECT * from user WHERE username = ?";
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setString(1, email);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	            	User user = new User();
	            	user.setName(rs.getString("name"));
	            	user.setEmail(rs.getString("username"));
	            	user.setUserType(rs.getString("type"));
	            	user.setNumPostedStudies(rs.getInt("studies"));
	            	user.setNumParticipation(rs.getInt("participation"));
	            	user.setNumCoins(rs.getInt("coins"));
	               return user;
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	            return null;
	        } finally {
	            DBUtil.closeResultSet(rs);
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	        return null;
	    }

	
	  public static List<User> getUsers() {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	       ArrayList<User> users = new ArrayList<User>();
	        String query = "SELECT * from user";
	        try {
	            ps = connection.prepareStatement(query);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	            	User user = new User();
	               	user.setName(rs.getString("name"));
	            	user.setEmail(rs.getString("username"));
	            	user.setUserType(rs.getString("type"));
	            	user.setNumPostedStudies(rs.getInt("studies"));
	            	user.setNumParticipation(rs.getInt("participation"));
	            	user.setNumCoins(rs.getInt("coins"));
	                users.add(user);
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	            return null;
	        } finally {
	            DBUtil.closeResultSet(rs);
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	        return users;
	    }

    public static int addUser(String username,String password,String type,int  studies,int participation,int coins,String name,String salt) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;

	        String query = "INSERT INTO user "
	                + "(Username, Password, Type, Studies, Participation, Coins, name,salt) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?,?)";
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setString(1,username );
	            ps.setString(2,password);
	            ps.setString(3,type);
	            ps.setInt(4,studies);
	            ps.setInt(5,participation );
	            ps.setInt(6, coins);
	            ps.setString(7,name);
                    ps.setString(8, salt);
	            return ps.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e);
	            return 0;
	        } finally {
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	    }
	 
	 public static void updateUserParticipation(String email, int parNum) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        String query = "UPDATE user SET "
	                + "Participation = ? "
	                + "WHERE username = ? ";
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setInt(1, parNum);
	            ps.setString(2, email);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e);
	          
	        } finally {
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	    }

	 public static void updateUserCoins(String email, int coins) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        String query = "UPDATE user SET "
	                + "coins = ? "
	                + "WHERE username = ? ";
               
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setInt(1, coins);
	            ps.setString(2, email);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e);
	          
	        } finally {
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	    }
          public static int tempuser(Temp user) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;

	        String query = "INSERT INTO tempUser "
	                + "(uName, password,email,token,issueDate) "
	                + "VALUES (?, ?, ?, ?,?)";
	        try {
	            ps = connection.prepareStatement(query);
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getPassword());
	            ps.setString(3, user.getEmail());
	            ps.setString(4, user.getToken());
                    ps.setTimestamp(5, user.getTime());

	           
	            
	            return ps.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e);
	            return 0;
	        } finally {
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	    }
 
          public static boolean checkcode(String token, String uName) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        String query = "select * FROM tempUser WHERE uName = ? and token = ?";
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setString(2, token);
	            ps.setString(1, uName);
	            rs= ps.executeQuery();
	            while (rs.next())
	            {
	             return true;
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	            return false;
	        } finally {
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
			return false;
	    }

          public static Temp gettempUser(String token) {
	         ConnectionPool pool = ConnectionPool.getInstance();
                Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        String query = "SELECT * from tempUser WHERE token = ?";
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setString(1,token);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	            	Temp user = new Temp();
	            	user.setName(rs.getString("uName"));
	            	user.setEmail(rs.getString("email"));
	            	//user.setUserType(rs.getString("type"));
	            	//user.setNumPostedStudies(rs.getInt("studies"));
	            	//user.setNumParticipation(rs.getInt("participation"));
	            	user.setToken(rs.getString("Token"));
                        user.setPassword(rs.getString("password"));
	               return user;
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	            return null;
	        } finally {
	            DBUtil.closeResultSet(rs);
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	        return null;
	    }
           public static void deletetemp(String token) {
               ConnectionPool pool = ConnectionPool.getInstance();
                Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
                String query = "DELETE from tempUser WHERE token = ?";
                try {
	            ps = connection.prepareStatement(query);
	            ps.setString(1,token);
      
                    ps.executeUpdate();
                }
               catch (SQLException e) {
	            System.out.println(e);
	           
	        } finally {
	           
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	       
}
           public static void resetpass(String email,String password) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        String query = "UPDATE user SET "
	                + "Password = ? "
	                + "WHERE username = ? ";
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setString(1, password);
	            ps.setString(2, email);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e);
	          
	        } finally {
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	    }
public static void bonus(String email, int coins) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        String query = "UPDATE user SET "
	                + "coins = ? "
	                + "WHERE username = ? ";
               int ucoins=coins+2;
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setInt(1, ucoins);
	            ps.setString(2, email);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e);
	          
	        } finally {
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
}
                public static int getCoins(String email) {
	         ConnectionPool pool = ConnectionPool.getInstance();
                Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        String query = "SELECT Coins from user WHERE username = ?";
	        try {
	            ps = connection.prepareStatement(query);
	            ps.setString(1, email);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	            	int coins=rs.getInt("Coins");
                        //User user = new User();
	            	//user.setName(rs.getString("name"));
	            	//user.setEmail(rs.getString("username"));
	            	//user.setUserType(rs.getString("type"));
	            	//user.setNumPostedStudies(rs.getInt("studies"));
	            	//user.setNumParticipation(rs.getInt("participation"));
	            	//user.setNumCoins(rs.getInt("coins"));
	               return coins;
	            }
	        } catch (SQLException e) {
	            System.out.println(e);
	            return 0;
	        } finally {
	            DBUtil.closeResultSet(rs);
	            DBUtil.closePreparedStatement(ps);
	            pool.freeConnection(connection);
	        }
	        return 0;
	    }
           }