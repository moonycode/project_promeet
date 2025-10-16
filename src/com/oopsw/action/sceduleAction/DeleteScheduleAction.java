package com.oopsw.action.sceduleAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ScheduleDAO;
import com.oopsw.model.ScheduleVO;

public class DeleteScheduleAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String scheduleNoStr = request.getParameter("scheduleNo");

		int scheduleNo = Integer.parseInt(scheduleNoStr);
		
	    ScheduleDAO dao = new ScheduleDAO();
	    boolean result = dao.deleteSchedule(scheduleNo);

		request.setAttribute("result", result);
		return "Json/jsonResult.jsp";
	}

}
