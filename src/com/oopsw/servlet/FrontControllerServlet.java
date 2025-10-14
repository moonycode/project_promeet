package com.oopsw.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oopsw.action.Action;
import com.oopsw.action.ActionFactory;

@WebServlet("/controller")
public class FrontControllerServlet extends HttpServlet {	
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, 
			HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd = request.getParameter("cmd");
		
		if(cmd == null || cmd.trim().length() == 0) {
			cmd="mainUI";
		}
		
		Action action = ActionFactory.getAction(cmd);
		String url = action.execute(request);
		request.getRequestDispatcher("/"+url).forward(request, response);
	}	
}