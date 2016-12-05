package Com.Controller;

import Com.Data.EmailDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Com.Data.StudyDB;
import Com.Data.UserDB;
import Com.Model.Temp;
import Com.Model.User;
import java.util.UUID;
import Com.Data.PasswordUtil;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UserController")

//userController 
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		// get current action
		String url = "/home.jsp";
		String action = request.getParameter("action");
		// if action is null goes to home.jsp

		
		if (action == null || action.equals("") || action.isEmpty()) {
			url = "/home.jsp";
			Cookie c = new Cookie("host", request.getRemoteHost());
			Cookie c1 = new Cookie("port", request.getRemotePort() + "");
			c.setMaxAge(60 * 60 * 24 * 365 * 10);
			c1.setMaxAge(60 * 60 * 24 * 365 * 10);
			response.addCookie(c);
			response.addCookie(c1);
			Cookie[] cookies = request.getCookies();
			if(cookies==null){
			   response.setIntHeader("Refresh", 1);}
		
		}

		
		else {
			if (action.equals("login")) {
				
                            
                                String email = request.getParameter("email");
                                String pwd = request.getParameter("password");
                                String[] checks=new String[2];
                                //User user=new User();
                                checks=UserDB.selectUser(email);
                                String slt=checks[0];
                                String opass=checks[1];
                                String eval=pwd+slt.trim();
                                
                                //System.out.println("Entered Username: " + email);
                                //System.out.println("Entered plaintext password: " + pwd + "<");
                                //System.out.println("Salt retrived: " + slt +  "<");
                                //System.out.println("Input to hashMethod: " + eval + "<");
                                
                                try{
                                String check=PasswordUtil.hashPassword(eval);
                                //System.out.println("User entered paswword hash " + check);
                                if(opass.equals(check)){
                                    // User userd=new User();
                                    User userd = UserDB.getUser(email);
                                    String userType = userd.getUserType();
                                    
                                    if (userType.equalsIgnoreCase("Participant")) {
                                        session.setAttribute("theUser", userd);
                                        int participants = StudyDB.getParticipants(userd.getEmail());
                                        session.setAttribute("par", participants);
                                        url = "/main.jsp";
                                    } else if (userType.equalsIgnoreCase("Admin")) {
                                        session.setAttribute("theAdmin", userd);
                                        url = "/admin.jsp";
                                    }
                                }
                            } catch (NoSuchAlgorithmException ex) {
                                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                            }
				} else {

					request.setAttribute("msg", "Email address and Password mis match");
					url = "/login.jsp";
				}

			
        
			if (action.equals("create")) {
				String email = request.getParameter("email");
				String name = request.getParameter("name");
                                
                               
				String password = request.getParameter("password");
				String cPassword = request.getParameter("confirm_password");
				if (UserDB.getUser(email) != null) {
					request.setAttribute("msg", "Email id already exists");
					request.setAttribute("email", email);
					request.setAttribute("name", name);
					url = "/signup.jsp";
				} else if (!password.equals(cPassword)) {
					request.setAttribute("msg", "Passwords dont match");
					request.setAttribute("email", email);
					request.setAttribute("name", name);
					url = "/signup.jsp";
				} 
                                
                                
                                 else {
                                    String id=UUID.randomUUID().toString();
                                    java.util.Date date = new java.util.Date();
                                   Timestamp time=new Timestamp(date.getTime());
				 Temp user=new Temp(name,email,password,id,time);
                                 UserDB.tempuser(user);
                                 EmailDB.emailnewuser(name,email,id);
                                 
                                 
                                 url="/activation.jsp";
                                } 
                        }
                                else if(action.equals("bonus")){
                                  String smail=request.getParameter("semail");
                                     String fmail=request.getParameter("femail");
                                     // String bonus=request.getParameter("bonus");
                                     //User buser=UserDB.getUser(smail);
                                     int coins=UserDB.getCoins(smail);
                                     //url="/signup.jsp";
                                     UserDB.bonus(smail,coins);
                                     
                                     url="/signup.jsp";
                            }
                                    //User user = new User(name, email, "Participant", password, cPassword, 0, 0, 0);
					//int participants = StudyDB.getParticipants(user.getEmail());
					//session.setAttribute("par", participants);
					//UserDB.addUser(user);
					//session.setAttribute("theUser", user);
					//url = "/main.jsp";
				
                                
                                
        
                        else if (action.equals("activate")) {
				String token=request.getParameter("token");
                                String name=request.getParameter("name");
                           if (UserDB.checkcode(token, name)) {
                                    try {
                                        Temp us=new Temp();
                                        us=UserDB.gettempUser(token);
                                        
                                        String email = us.getEmail();
                                        String password = us.getPassword();
                                        // PasswordUtil.hashandsaltPassword(password);
                                        // try {
                                        String hsp[]=PasswordUtil.hashandsaltPassword(password);
                                        //}
                                        //catch (NoSuchAlgorithmException ex) {
                                        //Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                                        //}
                                        UserDB.addUser(email,hsp[1],"Participant",0,0,0,name,hsp[0]);
                                        
                                        UserDB.deletetemp(token);
                                        //String securepass=PasswordUtil.hashPassword(password);
                                        User user=new User();
                                        user = UserDB.getUser(email);
                                        session.setAttribute("theUser", user);
                                        int participants =StudyDB.getParticipants(user.getEmail());
                                        session.setAttribute("par", participants);
                                      
                                        url = "/main.jsp";
                                    } catch (NoSuchAlgorithmException ex) {
                                        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                        
                           }
                           else {

					request.setAttribute("msg", "Account not created.Please try creating a new account");
					url = "/login.jsp";
                           }
			  		
                        }
                        if (action.equals("reset")){
                            String email=request.getParameter("email");
                            EmailDB.emailreset(email);
                            
                           url="/login.jsp";
                           request.setAttribute("msg","Password reset request sent to email");
                            
                                    }
                        if(action.equals("rpass"))
                        {
                            try {
                                String email=request.getParameter("email");
                                String pass=request.getParameter("password");
                                String[] checks=new String[2];
                                //User user=new User();
                                checks=UserDB.selectUser(email);
                                String slt=checks[0];
                                // String opass=checks[1];
                                String eval=pass+slt.trim();
                                String check=PasswordUtil.hashPassword(eval);
                                
                                UserDB.resetpass(email,check);
                                url="/login.jsp";
                            } catch (NoSuchAlgorithmException ex) {
                                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if(action.equals("npass"))
                        {
                            String mail=request.getParameter("email");
                            request.setAttribute("email",mail);

                              url="/newpass.jsp";
                        }
        
			if (action.equals("how")) {
				if (session.getAttribute("theUser") != null || session.getAttribute("theAdmin") != null) {
					url = "/main.jsp";
				} else {
					url = "/how.jsp";
				}
			}

			if (action.equals("about")) {
				if (session.getAttribute("theUser") != null || session.getAttribute("theAdmin") != null) {
					url = "/aboutl.jsp";
				} else {
					url = "/about.jsp";
				}
			}

			if (action.equals("home")) {
				if (session.getAttribute("theUser") != null || session.getAttribute("theAdmin") != null) {
					url = "/main.jsp";
				} else {
					url = "/home.jsp";
				}
			}

			if (action.equals("main")) {
				if (session.getAttribute("theUser") != null || session.getAttribute("theAdmin") != null) {
					url = "/main.jsp";
				} else {
					url = "/login.jsp";
				}
			}

			if (action.equals("logout")) {
				if (session.getAttribute("theUser") != null || session.getAttribute("theAdmin") != null) {
					session.invalidate();
				}
				url = "/home.jsp";
			}
        
                        
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
        }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

