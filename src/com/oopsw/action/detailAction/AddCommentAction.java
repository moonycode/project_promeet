package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentVO;

import util.CreateJsonResponse;
import util.JsonResponse;


public class AddCommentAction implements Action {
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNo = request.getParameter("taskNo");
		String contents = request.getParameter("contents");
		String employeeId = request.getParameter("employeeId");
		String fileName = request.getParameter("fileName");
		int projectJoinNo = new CommentReplyDAO().findProjectJoinNo(Integer.valueOf(taskNo), employeeId);
		
		
		try{
			JsonResponse<Integer> response = 
					new JsonResponse<>("success", "추가 완료",new CommentReplyDAO().addComment(new CommentVO(Integer.valueOf(taskNo),projectJoinNo,contents,fileName,null)));
			String jsonResponse = CreateJsonResponse.toJson(response);
			request.setAttribute("jsonResponse", jsonResponse);
		}catch(Exception e){
			e.printStackTrace();
		}	

		return "Json/jsonResult.jsp";
	}

}
