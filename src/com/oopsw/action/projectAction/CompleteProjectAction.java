package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;

public class CompleteProjectAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        
        String projectNoStr = request.getParameter("projectNo");
        
        if (projectNoStr == null || projectNoStr.isEmpty()) {
            return "controller?cmd=projectUI"; 
        }

        int projectNo = Integer.parseInt(projectNoStr);
        
        ProjectDAO dao = new ProjectDAO();
        int result = dao.completeProject(projectNo); 

        if (result > 0) {
            return "controller?cmd=projectUI";
        } else {
            return "controller?cmd=projectUI";
        }
    }
}