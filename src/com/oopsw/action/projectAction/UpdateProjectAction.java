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
        
        // 1. 요청 파라미터 받기
        String projectNoStr = request.getParameter("projectNo");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (projectNoStr == null || projectNoStr.isEmpty()) {
            return "controller?cmd=projectUI"; 
        }

        int projectNo = Integer.parseInt(projectNoStr);
        
        // 2. ProjectVO 객체에 데이터 설정 (DAO가 요구하는 VO 필드만)
        ProjectVO project = new ProjectVO();
        project.setProjectNo(projectNo);
        project.setProjectName(request.getParameter("projectName"));
        project.setClient(request.getParameter("client"));
        project.setDescription(request.getParameter("description"));
        
        // 3. DAO를 통해 DB 업데이트 실행
        ProjectDAO dao = new ProjectDAO();
        int result = dao.updateProject(project, startDate, endDate); 

        if (result > 0) {
            // 4. 수정 성공 시
            return "controller?cmd=selectProject&projectNo=" + projectNo;
        } else {
            // 5. 수정 실패 시
            return "controller?cmd=updateProjectUI&projectNo=" + projectNo;
        }
    }
}