package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;
import util.*;

public class RestoreProjectAction implements Action {
	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		int projectNo = Integer.parseInt(request.getParameter("projectNo"));
		int c = new ProjectDAO().restoreProject(projectNo);
		String json = CreateJsonResponse
				.toJson(new JsonResponse<Integer>(c == 1 ? "ok" : "fail", c == 1 ? "복원 완료" : "복원 실패", new Integer(c)));
		request.setAttribute("jsonResponse", json);
		return "Json/jsonResult.jsp";
	}
}