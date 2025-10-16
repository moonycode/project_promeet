package com.oopsw.action.fileboxAction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.FileBoxDAO;
import com.oopsw.model.FilesTaskFileBoxVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class filesTaskFileBoxAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNoStr = request.getParameter("taskNo");
		System.out.println(taskNoStr);
		int taskno = Integer.parseInt(taskNoStr);
		List<FilesTaskFileBoxVO> taskFiles = new FileBoxDAO().getTaskFiles(taskno);
		
		  JsonResponse<List<FilesTaskFileBoxVO>> response = new JsonResponse<>("success", "업무 조회", taskFiles);
		  String jsonResponse = CreateJsonResponse.toJson(response);
		request.setAttribute("jsonResponse", jsonResponse);
		
		return "Json/jsonResult.jsp";
	}

}
