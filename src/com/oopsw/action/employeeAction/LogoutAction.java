package com.oopsw.action.employeeAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oopsw.action.Action;

public class LogoutAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "login.jsp";
	}
}
