package com.oopsw.action.detailAction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;

import util.CreateJsonResponse;
import util.JsonResponse;


public class CheckListAction implements Action {
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNo = request.getParameter("taskNo");
		try{
			JsonResponse<List<CheckListVO>> response = 
					new JsonResponse<>("success", "검색 완료", new CheckListDAO().getChecklist(Integer.valueOf(taskNo)));
			String jsonResponse = CreateJsonResponse.toJson(response);
			request.setAttribute("jsonResponse", jsonResponse);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return "Json/jsonResult.jsp";
	}
}

