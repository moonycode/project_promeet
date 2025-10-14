package com.oopsw.action.detailAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentVO;

public class UpdateCommentAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String commentNo = request.getParameter("commentNo");
		String contents = request.getParameter("contents");
		int result = 0;

		try{
			result = new CommentReplyDAO().updateComment(new CommentVO(Integer.valueOf(commentNo),contents));
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "comment.jsp";
	}

}
