package com.oopsw.action.fileboxAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;

public class FileBoxUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		return "fileBox.jsp";
	}

}
