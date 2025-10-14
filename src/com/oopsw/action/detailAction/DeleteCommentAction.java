package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CommentReplyDAO;

public class DeleteCommentAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String commentNo = request.getParameter("commentNo");
		int result = 0;
		try{
			result = new CommentReplyDAO().deleteComment(Integer.valueOf(commentNo));
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "comment.jsp";
	}

}
