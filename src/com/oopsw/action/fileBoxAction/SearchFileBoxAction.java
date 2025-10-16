package com.oopsw.action.fileBoxAction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.FileBoxDAO;
import com.oopsw.model.SearchFileBoxVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class SearchFileBoxAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String keyword = request.getParameter("keyword");
		List<SearchFileBoxVO> files = new FileBoxDAO().searchFiles(keyword);
		
		  JsonResponse<List<SearchFileBoxVO>> response = new JsonResponse<>("success", "�˻� �Ϸ�", files);
		  String jsonResponse = CreateJsonResponse.toJson(response);
		request.setAttribute("jsonResponse", jsonResponse);
		return "Json/jsonResult.jsp";
	}

}
