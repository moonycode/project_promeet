package com.oopsw.action.projectAction;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeVO;
import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

public class BinProjectUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String url = "projectBin.jsp";
		
		// 세션에서 로그인 사용자 정보 호출
		EmployeeVO user = (EmployeeVO) request.getSession().getAttribute("user");
		
		if (user == null) {
			return "controller?cmd=loginUI";
		}
		
		String loginId = user.getEmployeeId(); 
		request.setAttribute("user", user); 
		
		// DB 오류 시 NullPointerException을 방지하기 위해 빈 리스트로 초기화
		List<ProjectVO> binProjects = Collections.emptyList();

		try {
			ProjectDAO projectDAO = new ProjectDAO();
			
			binProjects = projectDAO.getBinProjects(loginId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("binProjects", binProjects); // 휴지통 목록
		request.setAttribute("type", "bin"); // UI 상태 유지용

		return url;
	}
}