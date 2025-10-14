package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;

// cmd=completeProject를 처리하여 프로젝트를 완료 상태로 변경합니다.
public class CompleteProjectAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		// 완료 성공/실패와 관계없이 프로젝트 목록으로 리다이렉트
		String url = "controller?cmd=projectUI"; 
		
		// 1. 요청 파라미터(projectNo) 받기
		String projectNoStr = request.getParameter("projectNo");
		
		if (projectNoStr == null || projectNoStr.isEmpty()) {
			request.setAttribute("msg", "종료할 프로젝트 번호가 누락되었습니다.");
			return url;
		}
		
		try {
			int projectNo = Integer.parseInt(projectNoStr);
			
			// 2. DB에 프로젝트 완료 처리
			ProjectDAO projectDAO = new ProjectDAO();
			
            // DAO 메소드 호출 (completeProject 메소드가 있다고 가정)
            int result = projectDAO.completeProject(projectNo); 
			
			if (result > 0) {
				// 완료 성공 시 메시지 (필요하다면 세션에 저장하여 projectUI에서 출력)
				// request.getSession().setAttribute("msg", "프로젝트가 성공적으로 완료 처리되었습니다.");
			} else {
				// 완료 실패 시 메시지 (projectUI에서 출력하도록 설정)
				request.setAttribute("msg", "프로젝트 완료 처리 중 DB 오류가 발생했습니다.");
			}

		} catch (NumberFormatException e) {
			request.setAttribute("msg", "잘못된 프로젝트 번호 형식입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "프로젝트 완료 처리 중 시스템 오류 발생.");
		}

		return url; // projectUI로 최종 이동
	}
}