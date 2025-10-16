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

public class UpdateReplyAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String replyNo = request.getParameter("replyNo");
		String contents = request.getParameter("contents");

		try{
			JsonResponse<Integer> response = 
					new JsonResponse<>("success", "수정 완료",new CommentReplyDAO().updateReply(new ReplyVO(Integer.valueOf(replyNo),contents)));
			String jsonResponse = CreateJsonResponse.toJson(response);
			request.setAttribute("jsonResponse", jsonResponse);
		}catch(Exception e){
			e.printStackTrace();
		}	

		return "Json/jsonResult.jsp";
	}

}
