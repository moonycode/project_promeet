package com.oopsw.action.projectAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

public class AddProjectAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "controller?cmd=projectUI";
		
		try {
			ProjectVO projectVO = new ProjectVO();
			String creatorId = request.getParameter("creatorId");
			String projectName = request.getParameter("projectName");
			String client = request.getParameter("client");
			String description = request.getParameter("description");
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			
			projectVO.setCreatorId(creatorId);
			projectVO.setProjectName(projectName);
			projectVO.setClient(client);
			projectVO.setDescription(description);

			ProjectDAO projectDAO = new ProjectDAO();
			int result = projectDAO.insertProject(projectVO, startDateStr, endDateStr);
			
			if (result > 0) {
			} else {
				request.setAttribute("msg", "프로젝트 등록에 실패했습니다.");
				url = "addProject.jsp";
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "등록 중 시스템 오류가 발생했습니다.");
			url = "addProject.jsp";
		}

		return url;
	}
}