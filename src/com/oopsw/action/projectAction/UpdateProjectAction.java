package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

// cmd=updateProject를 처리하여 DB에 프로젝트 정보를 업데이트합니다.
public class UpdateProjectAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		// 수정 성공 시, 해당 프로젝트의 Task 페이지로 리다이렉트
		String projectNo = request.getParameter("projectNo");
		String url = "controller?cmd=taskUI&projectNo=" + projectNo; 
		
		try {
			// 1. 요청 파라미터 받기 및 VO에 설정
			ProjectVO projectVO = new ProjectVO();
            
            // 필수: projectNo를 int로 변환
            int pNo = Integer.parseInt(projectNo);
            projectVO.setProjectNo(pNo); 
            
			projectVO.setProjectName(request.getParameter("projectName"));
			projectVO.setClient(request.getParameter("client"));
			projectVO.setDescription(request.getParameter("description"));
			
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

			// 2. DB에 프로젝트 정보 업데이트 처리
			ProjectDAO projectDAO = new ProjectDAO();
			
            int result = projectDAO.updateProject(projectVO, startDate, endDate); 
			
			if (result > 0) {
				// 성공: Task 페이지로 리다이렉트 (TaskUI가 없으면 projectUI로 변경)
			} else {
				// 실패: 수정 폼으로 돌아가 에러 메시지 출력
				request.setAttribute("msg", "프로젝트 수정에 실패했습니다. DB 입력 오류.");
                // 수정 폼에서 데이터를 다시 보여주기 위해 projectVO를 request에 다시 저장
                request.setAttribute("project", projectVO);
				url = "updateProject.jsp"; 
			}

		} catch (NumberFormatException e) {
			request.setAttribute("msg", "잘못된 프로젝트 번호 형식입니다.");
			url = "controller?cmd=projectUI";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "프로젝트 수정 중 시스템 오류 발생.");
			url = "updateProject.jsp"; 
		}

		return url; 
	}
}