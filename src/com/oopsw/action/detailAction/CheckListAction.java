package com.oopsw.action.detailAction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentReplyVO;


public class CheckListAction implements Action {
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNo = request.getParameter("taskNo");
		try{
			request.setAttribute("list", new CheckListDAO().getChecklist(Integer.valueOf(taskNo)));
		}catch(Exception e){
			e.printStackTrace();
		}	
		return "checkList.jsp";
	}
}

