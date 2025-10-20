package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CommentReplyDAO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class DeleteReplyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String replyNo = request.getParameter("replyNo");
		try{
			JsonResponse<Integer> response = 
					new JsonResponse<>("success", "삭제 완료", new CommentReplyDAO().deleteReply(Integer.valueOf(replyNo)));
			String jsonResponse = CreateJsonResponse.toJson(response);
			request.setAttribute("jsonResponse", jsonResponse);
		}catch(Exception e){
			e.printStackTrace();
		}	

		return "Json/jsonResult.jsp";
	}

}
