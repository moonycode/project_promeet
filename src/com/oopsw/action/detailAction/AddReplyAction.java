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
		String taskNo = request.getParameter("taskNo");
		String commentNo = request.getParameter("commentNo");
		String contents = request.getParameter("contents");
		String employeeId = request.getParameter("employeeId");
		int projectJoinNo = new CommentReplyDAO().findProjectJoinNo(Integer.valueOf(taskNo), employeeId);
		try{
			JsonResponse<Integer> response = 
					new JsonResponse<>("success", "추가 완료",new CommentReplyDAO().addReply(new ReplyVO(Integer.valueOf(commentNo),projectJoinNo,contents,null,null)));
			String jsonResponse = CreateJsonResponse.toJson(response);
			request.setAttribute("jsonResponse", jsonResponse);
		}catch(Exception e){
			e.printStackTrace();
		}	

		return "Json/jsonResult.jsp";
	}

}
