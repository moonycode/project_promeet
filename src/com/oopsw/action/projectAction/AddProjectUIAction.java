package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;

public class AddProjectUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {

		return "addProject.jsp"; 
	}
}