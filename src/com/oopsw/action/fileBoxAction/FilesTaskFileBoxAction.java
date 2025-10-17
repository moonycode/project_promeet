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

public class FilesTaskFileBoxAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNoStr = request.getParameter("taskNo");
		System.out.println(taskNoStr);
		int taskno = Integer.parseInt(taskNoStr);
		List<SearchFileBoxVO> taskFiles = new FileBoxDAO().searchFilesOrTask(null,taskno);
		
		  JsonResponse<List<SearchFileBoxVO>> response = new JsonResponse<>("success", "���� ��ȸ", taskFiles);
		  String jsonResponse = CreateJsonResponse.toJson(response);
		request.setAttribute("jsonResponse", jsonResponse);
		
		return "Json/jsonResult.jsp";
	}

}
