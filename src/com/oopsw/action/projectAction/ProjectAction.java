package com.oopsw.action.projectAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

public class ProjectAction implements Action {

	@Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        
        String projectNoStr = request.getParameter("projectNo");
        if (projectNoStr == null || projectNoStr.isEmpty()) {
            return "controller?cmd=projectUI"; 
        }

        int projectNo = Integer.parseInt(projectNoStr);
        
        ProjectDAO dao = new ProjectDAO();
        ProjectVO project = dao.getProjectByNo(projectNo);

        if (project != null) {
            request.setAttribute("project", project);
            return "tasks.jsp";
        } else {
            return null;
        }
    }
}