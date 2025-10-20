package com.oopsw.action.employeeAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeDAO;
import com.oopsw.model.EmployeeVO;

public class LoginAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        // �α��� ���� �� ������Ʈ ���� ȭ������ �̵�
        String successUrl = "controller?cmd=projectUI";
        // �α��� ���� �� �α��� ȭ������ ������
        String failUrl = "login.jsp";

        try {
            // 1. ��û �Ķ���� �ޱ�
            String employeeId = request.getParameter("employeeId");
            String password = request.getParameter("password");
            
            // 2. DB���� ����� ��й�ȣ ��ġ ���� Ȯ�� �� ���� ��ȸ
            EmployeeDAO employeeDAO = new EmployeeDAO();
            EmployeeVO employeeVO = employeeDAO.loginCheck(employeeId, password);          
            
            if (employeeVO != null) {
                // 3. ���� ����: ���ǿ� ����� ���� ����
                HttpSession session = request.getSession();
                session.setAttribute("user", employeeVO); 
                
                return successUrl; // ������ cmd=projectUI�� �̵�
            } else {
                // 4. ���� ����
                request.setAttribute("msg", "��� �Ǵ� ��й�ȣ�� ��ġ���� �ʰų� ����� �����Դϴ�.");
                return failUrl; // login.jsp�� ������ (msg�� �Բ�)
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "�α��� ó�� �� �ý��� ������ �߻��߽��ϴ�.");
            return failUrl;
        }
    }
}