package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentVO;
import com.oopsw.model.ReplyVO;

public class UpdateReplyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String replyNo = request.getParameter("replyNo");
		String contents = request.getParameter("contents");
		int result = 0;

		try{
			result = new CommentReplyDAO().updateReply(new ReplyVO(Integer.valueOf(replyNo),contents));
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "reply.jsp";
	}

}
