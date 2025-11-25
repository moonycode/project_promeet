package com.oopsw.action.employeeAction;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeDAO;
import com.oopsw.model.EmployeeVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                EmployeeVO user = (EmployeeVO) session.getAttribute("user");
                if (user != null) {
                    // 로그아웃 시 자동 퇴근 처리
                    new EmployeeDAO().updateWorkStatus(user.getEmployeeId(), "퇴근");
                }
            } catch (Exception ignore) {}

            session.invalidate();
        }
        return "login.jsp";
    }
}
