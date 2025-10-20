package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class DeleteCheckListAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String checkListNo = request.getParameter("checkListNo");
		try{
			JsonResponse<Integer> response = 
					new JsonResponse<>("success", "삭제 완료", new CheckListDAO().deleteChecklist(Integer.valueOf(checkListNo)));
			String jsonResponse = CreateJsonResponse.toJson(response);
			request.setAttribute("jsonResponse", jsonResponse);

		}catch(Exception e){
			e.printStackTrace();
		}	

		return "Json/jsonResult.jsp";
	}

}
