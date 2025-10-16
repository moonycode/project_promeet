package com.oopsw.action.employeeAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeDAO;
import com.oopsw.model.EmployeeVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class ChangePasswordAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session = request.getSession();
		EmployeeVO loginUser = (EmployeeVO) session.getAttribute("user");

		String employeeId = loginUser.getEmployeeId();
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
        System.out.println(currentPassword);
        System.out.println(newPassword);

		JsonResponse<String> response;

		if (currentPassword == null || currentPassword.isEmpty() || 
				newPassword == null || newPassword.isEmpty()) {
			response = new JsonResponse<>("fail", "현재 및 새 비밀번호를 모두 입력해주세요.", null);
		} else {
			try {
				EmployeeDAO dao = new EmployeeDAO();
				int result = dao.updatePassword(employeeId, currentPassword, newPassword);

				if (result > 0) {
					loginUser.setPassword(newPassword);
					session.setAttribute("user", loginUser); 
					System.out.println("변경성공");
					response = new JsonResponse<>("success", "비밀번호가 성공적으로 변경되었습니다.", employeeId);
				} else {
					System.out.println("변경실패");
					response = new JsonResponse<>("fail", "현재 비밀번호가 일치하지 않거나 사원 정보가 유효하지 않습니다.", null);

				}
			} catch (Exception e) {
				e.printStackTrace(); 
				response = new JsonResponse<>("error", "비밀번호 변경 중 오류가 발생했습니다.", null);
			}
		}

		String jsonResponse = CreateJsonResponse.toJson(response);
		request.setAttribute("jsonResponse", jsonResponse);
		return "Json/jsonResult.jsp"; 
	}

}
