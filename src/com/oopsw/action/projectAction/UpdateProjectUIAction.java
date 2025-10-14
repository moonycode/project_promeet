package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

public class UpdateProjectUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String projectNoStr = request.getParameter("projectNo");
		String url = "updateProject.jsp"; // 응답 JSP 파일
		
		if (projectNoStr == null || projectNoStr.isEmpty()) {
			// projectNo가 없으면 목록으로 돌려보내거나 에러 메시지를 표시
			request.setAttribute("msg", "수정할 프로젝트를 선택해주세요.");
			return "controller?cmd=projectUI";
		}
		
		try {
			int projectNo = Integer.parseInt(projectNoStr);
			// 2. DB에서 해당 projectNo의 데이터 조회 (DAO 메소드 필요)
			ProjectDAO projectDAO = new ProjectDAO();
			// DAO 메소드 호출 (getProjectByNo 메소드가 있다고 가정)
			ProjectVO projectVO = projectDAO.selectUpdateProject(projectNo);
			
			if (projectVO != null) {
				// 3. 조회된 데이터를 request에 저장하여 JSP로 포워딩
				request.setAttribute("project", projectVO);
			} else {
				// 조회 결과가 없으면 에러 메시지
				request.setAttribute("msg", "해당 프로젝트를 찾을 수 없습니다.");
				url = "controller?cmd=projectUI";
			}
			
		} catch (NumberFormatException e) {
			request.setAttribute("msg", "잘못된 프로젝트 번호 형식입니다.");
			url = "controller?cmd=projectUI";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "프로젝트 조회 중 시스템 오류 발생.");
			url = "controller?cmd=projectUI";
		}

		return url;
	}
}