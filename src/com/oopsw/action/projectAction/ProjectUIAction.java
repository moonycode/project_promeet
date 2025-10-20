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
	    
	    EmployeeVO user = (EmployeeVO) request.getSession().getAttribute("user");
	    
	    if (user == null) {
	        return "controller?cmd=loginUI";
	    }
	    
	    String loginId = user.getEmployeeId(); 
	    request.setAttribute("user", user); 
        
	    List<ProjectVO> ongoingProjects = Collections.emptyList();
	    List<ProjectVO> completedProjects = Collections.emptyList();

		try {
	        ProjectDAO projectDAO = new ProjectDAO();
	        
	        ongoingProjects = projectDAO.getOngoingProjects(loginId);
	        completedProjects = projectDAO.getCompletedProjects(loginId);

		} catch (Exception e) {
	        e.printStackTrace();
		}

	    request.setAttribute("ongoingProjects", ongoingProjects);
	    request.setAttribute("completedProjects", completedProjects);
	    request.setAttribute("type", "ongoing");

		return url;
	}
}