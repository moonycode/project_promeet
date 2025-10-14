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
        // 로그인 성공 시 프로젝트 메인 화면으로 이동
        String successUrl = "controller?cmd=projectUI";
        // 로그인 실패 시 로그인 화면으로 포워딩
        String failUrl = "login.jsp";

        try {
            // 1. 요청 파라미터 받기
            String employeeId = request.getParameter("employeeId");
            String password = request.getParameter("password");
            
            // 2. DB에서 사번과 비밀번호 일치 여부 확인 및 정보 조회
            EmployeeDAO employeeDAO = new EmployeeDAO();
            EmployeeVO employeeVO = employeeDAO.loginCheck(employeeId, password);          
            
            if (employeeVO != null) {
                // 3. 인증 성공: 세션에 사용자 정보 저장
                HttpSession session = request.getSession();
                session.setAttribute("user", employeeVO); 
                
                return successUrl; // 성공시 cmd=projectUI로 이동
            } else {
                // 4. 인증 실패
                request.setAttribute("msg", "사번 또는 비밀번호가 일치하지 않거나 퇴사한 직원입니다.");
                return failUrl; // login.jsp로 포워딩 (msg와 함께)
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "로그인 처리 중 시스템 오류가 발생했습니다.");
            return failUrl;
        }
    }
}