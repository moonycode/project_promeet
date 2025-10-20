package com.oopsw.action.employeeAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeDAO;
import com.oopsw.model.EmployeeVO;

public class MyPageUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("user");

		if (loginUser == null) {
			return "login.jsp";
		}
		
		String employeeId = loginUser.getEmployeeId();
		try {
			EmployeeDAO employeeDAO = new EmployeeDAO();
			EmployeeVO myPageInfo = employeeDAO.getEmployeeInfo(employeeId);
			
			if (myPageInfo != null) {
				request.setAttribute("myPageInfo", myPageInfo);
				return "myPage.jsp"; 
			} else {
				return "myPage.jsp"; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "myPage.jsp";
		}
	}
}