package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;

public class DeleteProjectAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		// 삭제 성공/실패와 관계없이 프로젝트 목록으로 리다이렉트
		String url = "controller?cmd=projectUI"; 
		
		// 1. 요청 파라미터(projectNo) 받기
		String projectNoStr = request.getParameter("projectNo");
		
		if (projectNoStr == null || projectNoStr.isEmpty()) {
			request.setAttribute("msg", "삭제할 프로젝트 번호가 누락되었습니다.");
			return url;
		}
		
		try {
			int projectNo = Integer.parseInt(projectNoStr);
			
			// 2. DB에 프로젝트 삭제 처리
			ProjectDAO projectDAO = new ProjectDAO();

            int result = projectDAO.deleteProject(projectNo); 
			if (result > 0) {
				// 성공: projectUI로 이동하여 갱신된 목록 표시
			} else {
				// 실패: 에러 메시지
				request.setAttribute("msg", "프로젝트 삭제 처리 중 DB 오류가 발생했거나, 이미 삭제된 프로젝트입니다.");
			}

		} catch (NumberFormatException e) {
			request.setAttribute("msg", "잘못된 프로젝트 번호 형식입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "프로젝트 삭제 처리 중 시스템 오류 발생.");
		}

		return url;
	}
}