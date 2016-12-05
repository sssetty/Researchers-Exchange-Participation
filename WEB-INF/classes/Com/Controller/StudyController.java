package Com.Controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
//import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import Com.Data.DBUtil;
import Com.Data.EmailDB;
import Com.Data.ReportDB;
import Com.Data.StudyDB;
import Com.Data.UserDB;
import Com.Model.Answer;
import Com.Model.Report;
import Com.Model.Study;
import Com.Model.User;

@WebServlet("/StudyController")
public class StudyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudyController() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// get current action
		String action = request.getParameter("action");
		String url = "/home.jsp";
		if (action == null || action.equals("") || action.isEmpty()) {
			if (session.getAttribute("theUser") != null) {
				url = "/main.jsp";
			} else {
				if (session.getAttribute("theAdmin") != null) {
					url = "/admin.jsp";
				} else {
					url = "/home.jsp";
				}
			}
		}


 
		if (action.equals("participate")) {
			if (session.getAttribute("theUser") != null) {
				String studyCode = request.getParameter("StudyCode");
				if (studyCode == null) {
					List<Study> studies = StudyDB.getStudiesByStatus("start");
					request.setAttribute("studies", studies);
					url = "/participate.jsp";
				} else {
					Study study = StudyDB.getStudy(studyCode);
					request.setAttribute("study", study);
					url = "/question.jsp";
				}

			} else {
				url = "/login.jsp";
			}
		}
		

		
		if (action.equals("edit")) {
			if (session.getAttribute("theUser") != null) {
				String studyCode = request.getParameter("StudyCode");
				String email = request.getParameter("email");
				if (studyCode != null) {
					Study study = StudyDB.getStudy(studyCode, email);
					request.setAttribute("study", study);
					url = "/editstudy.jsp";
				}

			} else {
				url = "/login.jsp";
			}
		}
		
		
		if (action.equals("report")) {
			if (session.getAttribute("theUser") != null) {
				String studyCode = request.getParameter("StudyCode");
				String email = request.getParameter("email");
				if (studyCode == null) {
					User user = (User) session.getAttribute("theUser");
					email = user.getEmail();
					List<Report> repList = ReportDB.getReports(email);
					request.setAttribute("repList", repList);
					url = "/reporth.jsp";
				} else {
					String ques = request.getParameter("ques");
					SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
					Study study=StudyDB.getStudy(studyCode);
					if (ReportDB.getReport(studyCode, email) != null) {
						request.setAttribute("msg", "Already");
						url = "/confirmrep.jsp";

					} else {
						java.util.Date date= new java.util.Date();
						Report report = new Report(studyCode, ques, email, new Timestamp(date.getTime()), "Pending",study.getStudyCode()+""+study.getStudyName());
						ReportDB.addReport(report);
						url = "/confirmrep.jsp";
					}
				}

			} else {
				url = "/login.jsp";
			}
		}

		
		if (action.equals("reportques")) {

			List<Report> reports = ReportDB.getReports();
			request.setAttribute("reports", reports);
			url = "/reportques.jsp";
		}

		
		if (action.equals("approve")) {
			if (session.getAttribute("theAdmin") != null) {
				String studyCode = request.getParameter("StudyCode");
				String repmail = request.getParameter("repemail");
				if (studyCode != null) {
					ReportDB.updateReportStatus(studyCode, repmail,"approved");
					//StudyDB.removeStudy(studyCode);
				}
				List<Report> repList = ReportDB.getReports();
				request.setAttribute("reports", repList);
				url = "/reportques.jsp";
			} else {
				url = "/login.jsp";
			}
		}

		
		if (action.equals("disapprove")) {
			if (session.getAttribute("theAdmin") != null) {
				String studyCode = request.getParameter("StudyCode");
				String repmail = request.getParameter("repemail");
				if (studyCode != null) {
					ReportDB.updateReportStatus(studyCode, repmail,"disapproved");
				}
				List<Report> repList = ReportDB.getReports();
				request.setAttribute("reports", repList);
				url = "/reportques.jsp";
			} else {
				url = "/login.jsp";
			}
		}

		
		if (action.equals("update")) {
			if (session.getAttribute("theUser") != null) {
				String name = request.getParameter("study_name");
				String ques = request.getParameter("question_text");
				String participantsNum = request.getParameter("participants");
				int parNum = Integer.parseInt(participantsNum);
				String participantsofNum = request.getParameter("numparticipants");
				int participNum = Integer.parseInt(participantsofNum);
				String AnswersNum = request.getParameter("edit_study_answers");
				int ansNum = Integer.parseInt(AnswersNum);
				String description = request.getParameter("description");
				String email = request.getParameter("email");
				String sCode = request.getParameter("code");
				String status = request.getParameter("status");
				//List ans = new ArrayList<>();
                                List answer = new ArrayList();
				for (int i = 1; i <= ansNum; i++) {
					answer.add(request.getParameter("DynamicTextBox" + i));
				}
				Study study = new Study(name, email, ques, parNum, participNum, description, answer, sCode, status);

				StudyDB.updateStudy(sCode, study);
				List<Study> studyList = StudyDB.getStudies(email);
				request.setAttribute("studyList", studyList);
				url = "/studies.jsp";

			} else {
				url = "/login.jsp";
			}
		}
		 
		
		if (action.equals("add")) {
			if (session.getAttribute("theUser") != null) {
				String name = request.getParameter("study_name");
				String ques = request.getParameter("question_text");
				String participantsNum = request.getParameter("participant_text");
				int parNum = Integer.parseInt(participantsNum);
				String AnswersNum = request.getParameter("answers");
				int ansNum = Integer.parseInt(AnswersNum);
				String description = request.getParameter("description");
				String email = request.getParameter("email");
				int x = (int) (Math.random() * 100);
				String sCode = "X" + x;
				sCode=StudyDB.getSCode(sCode);
				String status = "start";
				List ans = new ArrayList();
				for (int i = 1; i <= ansNum; i++) {
					ans.add(request.getParameter("DynamicTextBox" + i));
				}
				Study study = new Study(name, email, ques, parNum, 0, description, ans, sCode, status);
				StudyDB.addStudy(study);
				List<Study> studyList = StudyDB.getStudies(email);
				request.setAttribute("studyList", studyList);
				url = "/studies.jsp";
			}
		}
		
		
		if (action.equals("start")) {
			if (session.getAttribute("theUser") != null) {
				String studyCode = request.getParameter("StudyCode");
				String email = request.getParameter("email");
				if (studyCode != null) {
					
					StudyDB.updateStudyStatus(studyCode, email,"start");
				}
				List<Study> studyList = StudyDB.getStudies(email);
				request.setAttribute("studyList", studyList);
				url = "/studies.jsp";

			} else {
				url = "/login.jsp";
			}
		}
		
		
		if (action.equals("stop")) {
			if (session.getAttribute("theUser") != null) {
				String studyCode = request.getParameter("StudyCode");
				String email = request.getParameter("email");
				if (studyCode != null) {
					// request.setAttribute("studystatus", "stop");
					//StudyDB.getStudy(studyCode, email).setStatus("stop");
					StudyDB.updateStudyStatus(studyCode, email,"stop");
				}
				List<Study> studyList = StudyDB.getStudies(email);
				request.setAttribute("studyList", studyList);
				url = "/studies.jsp";

			} else {
				url = "/login.jsp";
			}
		}
		
	
		
		if (action.equals("answer")) {
			if (session.getAttribute("theUser") != null) {
				User user = (User) session.getAttribute("theUser");
				
				UserDB.updateUserParticipation((user.getEmail()), (user.getNumParticipation() + 1));
				UserDB.updateUserCoins((user.getEmail()), (user.getNumCoins() + 1));
				session.setAttribute("theUser", UserDB.getUser(user.getEmail()));
				String studyCode = request.getParameter("StudyCode");
				Study stud = StudyDB.getStudy(studyCode);
				StudyDB.updateStudyPar(studyCode, stud.getNumOfParticipants() + 1);
				if (!stud.getEmail().equals(user.getEmail())) {
					UserDB.updateUserCoins((stud.getEmail()), (UserDB.getUser(stud.getEmail()).getNumCoins() + 1));
				}
				String email = request.getParameter("email");
				String choice = request.getParameter("radname");
				java.util.Date date = new java.util.Date();
				Answer ans = new Answer(studyCode, email, new Timestamp(date.getTime()), choice,(stud.getStudyCode() + "" + stud.getStudyName()));
				StudyDB.addAnswers(ans);
				List<Study> studyList = StudyDB.getStudiesByStatus("start");
				request.setAttribute("studies", studyList);
				System.out.println(user.getNumParticipation()+"@@@@@"+user.getNumCoins());
				url = "/participate.jsp";
			} else {
				url = "/login.jsp";
			}

		}
		

		
		if (action.equals("studies")) {
			if (session.getAttribute("theUser") != null) {

				User user = (User) session.getAttribute("theUser");
				List<Study> studyList = StudyDB.getStudies(user.getEmail());
				request.setAttribute("studyList", studyList);
				url = "/studies.jsp";

			} else {
				url = "/login.jsp";
			}
		}
		
		if(action.equals("recommend"))
		{
			//String name=request.getParameter("study_name");
			String sMail=request.getParameter("email");
			String fMail=request.getParameter("friend_email");
			//String message=request.getParameter("message");
			EmailDB.emailRecommendTrigger(sMail, fMail);
			url="/confirmr.jsp";
		}
		if(action.equals("contact"))
		{
			String name=request.getParameter("study_name");
			String sMail=request.getParameter("email");
			String message=request.getParameter("message");
			EmailDB.emailContactTrigger(name, sMail, message);
			url="/confirmc.jsp";
		}

		getServletContext().getRequestDispatcher(url).forward(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
