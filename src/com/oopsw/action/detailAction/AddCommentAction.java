package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentVO;


public class AddCommentAction implements Action {
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNo = request.getParameter("taskNo");
		String contents = request.getParameter("contents");
		String projectJoinNo = request.getParameter("projectJoinNo");
		int result = 0;

		try{
			result = new CommentReplyDAO().addComment(new CommentVO(Integer.valueOf(taskNo),Integer.valueOf(projectJoinNo),contents,null,null));
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "comment.jsp";
	}

}
