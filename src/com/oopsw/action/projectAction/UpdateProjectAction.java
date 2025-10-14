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
        
        // 1. ��û �Ķ���� �ޱ�
        String projectNoStr = request.getParameter("projectNo");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (projectNoStr == null || projectNoStr.isEmpty()) {
            return "controller?cmd=projectUI"; 
        }

        int projectNo = Integer.parseInt(projectNoStr);
        
        // 2. ProjectVO ��ü�� ������ ���� (DAO�� �䱸�ϴ� VO �ʵ常)
        ProjectVO project = new ProjectVO();
        project.setProjectNo(projectNo);
        project.setProjectName(request.getParameter("projectName"));
        project.setClient(request.getParameter("client"));
        project.setDescription(request.getParameter("description"));
        
        // 3. DAO�� ���� DB ������Ʈ ����
        ProjectDAO dao = new ProjectDAO();
        int result = dao.updateProject(project, startDate, endDate); 

        if (result > 0) {
            // 4. ���� ���� ��
            return "controller?cmd=selectProject&projectNo=" + projectNo;
        } else {
            // 5. ���� ���� ��
            return "controller?cmd=updateProjectUI&projectNo=" + projectNo;
        }
    }
}