package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;

public class DeleteProjectAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        
        String projectNoStr = request.getParameter("projectNo");
        
        if (projectNoStr == null || projectNoStr.isEmpty()) {
            return "controller?cmd=projectUI"; 
        }

        int projectNo = Integer.parseInt(projectNoStr);
        
        ProjectDAO dao = new ProjectDAO();
        int result = dao.deleteProject(projectNo); 

        if (result > 0) {
            // 삭제 성공 시
            return "controller?cmd=projectUI";
        } else {
            // 삭제 실패 시
            return "controller?cmd=projectUI";
        }
    }
}