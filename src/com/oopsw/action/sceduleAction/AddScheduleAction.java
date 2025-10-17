package com.oopsw.action.sceduleAction;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeVO;
import com.oopsw.model.ScheduleDAO;
import com.oopsw.model.ScheduleVO;

public class AddScheduleAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String employeeId = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
		    EmployeeVO user = (EmployeeVO) session.getAttribute("user");
		    if (user != null) {
		        employeeId = (String)user.getEmployeeId();
		    }
		}
		String date = request.getParameter("scheduleDate");
		String scheduleType = request.getParameter("scheduleType");
		String title = request.getParameter("title");
		String start = request.getParameter("startTime");
		String end = request.getParameter("endTime");

		Date scheduleDate = Date.valueOf(date);
		Timestamp startTime = Timestamp.valueOf(date + " " + start + ":00");
		Timestamp endTime = Timestamp.valueOf(date + " " + end + ":00");

		boolean result = false;
		try {
			new ScheduleDAO()
					.addSchedule(new ScheduleVO(employeeId, scheduleDate, scheduleType, title, startTime, endTime));
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		request.setAttribute("result", result);
		return "Json/jsonResult.jsp";

	}
}
