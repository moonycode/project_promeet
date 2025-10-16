package com.oopsw.action.fileBoxAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.FileBoxDAO;
import com.oopsw.model.ProjectVO;
import com.oopsw.model.SearchFileBoxVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class FileBoxAction implements Action {
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		List<ProjectVO> projectList = new FileBoxDAO().getProjects();
	    
	    JsonResponse<List<ProjectVO>> response = 
	    	    new JsonResponse<>("success", "조회 완료", projectList);
	    	String jsonResponse = CreateJsonResponse.toJson(response);
	    	request.setAttribute("jsonResponse", jsonResponse);
	    	return "Json/jsonResult.jsp";

		

	}

}
