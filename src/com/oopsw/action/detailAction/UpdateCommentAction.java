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

import util.CreateJsonResponse;
import util.JsonResponse;

public class UpdateCommentAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String commentNo = request.getParameter("commentNo");
		String contents = request.getParameter("contents");

		try{
			JsonResponse<Integer> response = 
					new JsonResponse<>("success", "수정 완료",new CommentReplyDAO().updateComment(new CommentVO(Integer.valueOf(commentNo),contents)));
			String jsonResponse = CreateJsonResponse.toJson(response);
			request.setAttribute("jsonResponse", jsonResponse);
		}catch(Exception e){
			e.printStackTrace();
		}	

		return "Json/jsonResult.jsp";
	}

}
