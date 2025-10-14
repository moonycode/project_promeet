package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentVO;
import com.oopsw.model.ReplyVO;

public class AddReplyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String commentNo = request.getParameter("commentNo");
		String contents = request.getParameter("contents");
		String projectJoinNo = request.getParameter("projectJoinNo");
		int result = 0;

		try{
			result = new CommentReplyDAO().addReply(new ReplyVO(Integer.valueOf(commentNo),Integer.valueOf(projectJoinNo),contents,null,null));
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "reply.jsp";
	}

}
