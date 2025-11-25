package com.oopsw.action.employeeAction;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeDAO;
import com.oopsw.model.EmployeeVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateWorkStatusAction implements Action {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "controller?cmd=loginUI";
        }

        EmployeeVO user = (EmployeeVO) session.getAttribute("user");
        String status = request.getParameter("status");
        if (status == null) status = "출근";

        if (!"출근".equals(status) && !"자리비움".equals(status) && !"외근".equals(status) && !"퇴근".equals(status)) {
            status = "출근";
        }

        new EmployeeDAO().updateWorkStatus(user.getEmployeeId(), status);

        // 세션 동기화
        user.setWorkStatus(status);
        session.setAttribute("user", user);

        // Ajax 응답
        request.setAttribute("jsonResponse", "{\"status\":\"ok\"}");
        return "/WebContent/Json/jsonResult.jsp";
    }
}
