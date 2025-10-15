package com.oopsw.action.sceduleAction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oopsw.action.Action;
import com.oopsw.model.ScheduleDAO;
import com.oopsw.model.ScheduleVO;

public class DotScheduleAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
//	    HttpSession session = request.getSession();
//	    String employeeId = (String) session.getAttribute("employeeId");
		String employeeId = "1004014";
		
        ScheduleDAO dao = new ScheduleDAO();
        List<ScheduleVO> dotSchedulesList = dao.getDotSchedule(employeeId);
        request.setAttribute("dotSchedulesList", dotSchedulesList);
        return "Json/dotSchedule.jsp";
	}

}
