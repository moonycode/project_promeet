package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentVO;
import com.oopsw.model.ReplyVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class AddReplyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String commentNo = request.getParameter("commentNo");
		String contents = request.getParameter("contents");
		String projectJoinNo = request.getParameter("projectJoinNo");
		try{
			JsonResponse<Integer> response = 
					new JsonResponse<>("success", "추가 완료",new CommentReplyDAO().addReply(new ReplyVO(Integer.valueOf(commentNo),Integer.valueOf(projectJoinNo),contents,null,null)));
			String jsonResponse = CreateJsonResponse.toJson(response);
			request.setAttribute("jsonResponse", jsonResponse);
		}catch(Exception e){
			e.printStackTrace();
		}	

		return "Json/jsonResult.jsp";
	}

}
