package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

public class UpdateProjectAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        
        String projectNoStr = request.getParameter("projectNo");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (projectNoStr == null || projectNoStr.isEmpty()) {
            return "controller?cmd=projectUI"; 
        }

        int projectNo = Integer.parseInt(projectNoStr);
        ProjectVO project = new ProjectVO();
        project.setProjectNo(projectNo);
        project.setProjectName(request.getParameter("projectName"));
        project.setClient(request.getParameter("client"));
        project.setDescription(request.getParameter("description"));
        
        ProjectDAO dao = new ProjectDAO();
        int result = dao.updateProject(project, startDate, endDate); 

        if (result > 0) {
            return "controller?cmd=tasksUI&projectNo=" + projectNo;
        } else {
            return "controller?cmd=updateProjectUI&projectNo=" + projectNo;
        }
    }
}