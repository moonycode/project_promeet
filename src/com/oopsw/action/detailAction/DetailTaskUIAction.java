package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;

public class DetailTaskUIAction implements Action{
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		//혹시 처리가 있다면 구현코드가 들어가면 되는데 애는 단순히 연결만 하는거라 구현코드가 필요없다.
		return "details.jsp";
	}
	
}
