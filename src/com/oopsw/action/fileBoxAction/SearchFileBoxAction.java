package com.oopsw.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.model.FileBoxDAO;
import com.oopsw.model.SearchFileBoxVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class searchFileBoxAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String keyword = request.getParameter("keyword");
		System.out.println(keyword);
		FileBoxDAO dao = new FileBoxDAO();
		List<SearchFileBoxVO> files = dao.searchFiles(keyword);
		
		  JsonResponse<List<SearchFileBoxVO>> response = 
				  new JsonResponse<>("success", "검색 완료", files);
		  
		  String jsonResponse = CreateJsonResponse.toJson(response);
		request.setAttribute("jsonResponse", jsonResponse);
		System.out.println(jsonResponse);
		return "Json/jsonResult.jsp";
	}

}
