package com.oopsw.action.projectAction;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat; // SimpleDateFormat �߰�
import java.util.Collections;
import java.util.List;

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
			// 1. ��û �Ķ���� ȹ�� (��� String���� ����)
			ProjectVO projectVO = new ProjectVO();
			String creatorId = request.getParameter("creatorId");
			String projectName = request.getParameter("projectName");
			String client = request.getParameter("client");
			String description = request.getParameter("description");
			// ��¥�� String���� �޽��ϴ�.
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");

			// 2. �ʼ��� ��ȿ�� �˻�
			if (projectName == null || projectName.trim().isEmpty()) {
				
				request.setAttribute("msg", "������Ʈ �̸��� �ʼ� �׸��Դϴ�.");
				return "addProject.jsp"; 
			}
			
			// 3. ProjectVO�� �� ����
			projectVO.setCreatorId(creatorId);
			projectVO.setProjectName(projectName);
			projectVO.setClient(client);
			projectVO.setDescription(description);

			// 4. DB ��� ó��
			ProjectDAO projectDAO = new ProjectDAO();
			// DAO�� String Ÿ�� �״�� ����
			int result = projectDAO.insertProject(projectVO, startDateStr, endDateStr);
			
			if (result > 0) {
				// ��� ���� 
			} else {
				request.setAttribute("msg", "������Ʈ ��Ͽ� �����߽��ϴ�.");
				url = "addProject.jsp";
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "��� �� �ý��� ������ �߻��߽��ϴ�.");
			url = "addProject.jsp";
		}

		return url;
	}
}