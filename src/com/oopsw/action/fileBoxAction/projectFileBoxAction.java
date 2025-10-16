package com.oopsw.action.fileboxAction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.FileBoxDAO;
import com.oopsw.model.ProjectFileBoxVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class projectFileBoxAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String projectNoStr = request.getParameter("projectNo");
		int projectNo = Integer.parseInt(projectNoStr);
		List<ProjectFileBoxVO> projectFiles = new FileBoxDAO().getProjectNameTaskFileCount(projectNo);
		
		  JsonResponse<List<ProjectFileBoxVO>> response = new JsonResponse<>("success", "업무 조회", projectFiles);
		  String jsonResponse = CreateJsonResponse.toJson(response);
		request.setAttribute("jsonResponse", jsonResponse);
		return "Json/jsonResult.jsp";
	}

}
