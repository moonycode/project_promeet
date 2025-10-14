package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CommentReplyDAO;

public class DeleteReplyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String replyNo = request.getParameter("replyNo");
		int result = 0;
		try{
			result = new CommentReplyDAO().deleteReply(Integer.valueOf(replyNo));
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "reply.jsp";
	}

}
