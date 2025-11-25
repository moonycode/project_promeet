package com.oopsw.action.employeeAction;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeDAO;
import com.oopsw.model.EmployeeVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        final String successUrl = "controller?cmd=projectUI";
        final String failUrl    = "login.jsp";

        try {
            // (POST일 때) 한글 파라미터 방지용
            try { request.setCharacterEncoding("UTF-8"); } catch (Exception ignore) {}

            String employeeId = request.getParameter("employeeId");
            String password   = request.getParameter("password");

            EmployeeDAO employeeDAO = new EmployeeDAO();
            EmployeeVO  user        = employeeDAO.loginCheck(employeeId, password);

            if (user != null) {
                // 로그인 성공 → 자동 출근 처리
                try {
                    employeeDAO.updateWorkStatus(user.getEmployeeId(), "출근");
                    // 화면에서 바로 보이도록 세션에 넣는 객체에도 상태 반영
                    user.setWorkStatus("출근");
                } catch (Exception ignore) {}

                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

                return successUrl;
            } else {
                request.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않거나, 퇴사 처리된 계정입니다.");
                return failUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "로그인 처리 중 시스템 오류가 발생했습니다.");
            return failUrl;
        }
    }
}
