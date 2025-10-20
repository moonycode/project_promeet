package com.oopsw.action.detailAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class AddCheckListAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNo = request.getParameter("taskNo");
		String[] contents = request.getParameterValues("contents"); // n개의 contents가 넘어올 수 있음
		List<CheckListVO> list = new ArrayList<>();
		try{
			if(contents != null){
				for (String content : contents) {
					if (content != null && !content.trim().isEmpty()) {
						list.add(new CheckListVO(Integer.valueOf(taskNo), content));
					}
				}
				JsonResponse<Integer> response = 
						new JsonResponse<>("success", "추가 완료",new CheckListDAO().addChecklist(list));
				String jsonResponse = CreateJsonResponse.toJson(response);
				request.setAttribute("jsonResponse", jsonResponse);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		return "Json/jsonResult.jsp";
	}

}
