package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.TaskDAO;
import com.oopsw.model.TaskVO;

public class DetailTaskUIAction implements Action{
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		int taskNo = Integer.parseInt(request.getParameter("taskNo"));

        TaskVO task = new TaskDAO().selectTaskDetail(taskNo);
        request.setAttribute("task", task);
		return "details.jsp";
	}
	
}
