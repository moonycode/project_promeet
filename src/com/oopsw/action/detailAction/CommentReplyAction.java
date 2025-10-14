package com.oopsw.action.detailAction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentReplyVO;

public class CommentReplyAction implements Action {
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNo = request.getParameter("taskNo");
		List<CommentReplyVO> list= null;
		try{
			request.setAttribute("list", new CommentReplyDAO().getComments(Integer.valueOf(taskNo)));
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		return "commentReply.jsp";
	}
}
