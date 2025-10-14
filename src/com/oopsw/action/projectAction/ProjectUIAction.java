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

public class ProjectUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
	    String url = "projects.jsp";
	    
	    // 세션에서 로그인 사용자 정보 호출
	    EmployeeVO user = (EmployeeVO) request.getSession().getAttribute("user");
	    
	    if (user == null) {
	        // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
	        return "controller?cmd=loginUI";
	    }
	    
	    // 현재 로그인된 사원의 ID를 DAO에 전달
	    String loginId = user.getEmployeeId(); 
	    request.setAttribute("user", user); 
        
	    // DB 오류 시 NullPointerException을 방지하기 위해 빈 리스트로 초기화
	    List<ProjectVO> ongoingProjects = Collections.emptyList();
	    List<ProjectVO> completedProjects = Collections.emptyList();

		try {
	        ProjectDAO projectDAO = new ProjectDAO();
	        
	        ongoingProjects = projectDAO.getOngoingProjects(loginId);
	        completedProjects = projectDAO.getCompletedProjects(loginId);

		} catch (Exception e) {
	        e.printStackTrace();
		}

	    request.setAttribute("ongoingProjects", ongoingProjects); // 진행중 목록
	    request.setAttribute("completedProjects", completedProjects); // 완료 목록
	    request.setAttribute("type", "ongoing"); // UI 상태 유지용

		return url;
	}
}