package com.oopsw.action.projectAction;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat; // SimpleDateFormat 추가
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
			// 1. 요청 파라미터 획득 (모두 String으로 받음)
			ProjectVO projectVO = new ProjectVO();
			String creatorId = request.getParameter("creatorId");
			String projectName = request.getParameter("projectName");
			String client = request.getParameter("client");
			String description = request.getParameter("description");
			// 날짜를 String으로 받습니다.
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");

			// 2. 필수값 유효성 검사
			if (projectName == null || projectName.trim().isEmpty()) {
				
				request.setAttribute("msg", "프로젝트 이름은 필수 항목입니다.");
				return "addProject.jsp"; 
			}
			
			// 3. ProjectVO에 값 설정
			projectVO.setCreatorId(creatorId);
			projectVO.setProjectName(projectName);
			projectVO.setClient(client);
			projectVO.setDescription(description);

			// 4. DB 등록 처리
			ProjectDAO projectDAO = new ProjectDAO();
			// DAO에 String 타입 그대로 전달
			int result = projectDAO.insertProject(projectVO, startDateStr, endDateStr);
			
			if (result > 0) {
				// 등록 성공 
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