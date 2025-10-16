package com.oopsw.action.sceduleAction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ScheduleDAO;
import com.oopsw.model.ScheduleVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class DotScheduleAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    
        
//	    HttpSession session = request.getSession();
//	    String employeeId = (String) session.getAttribute("employeeId");
		String employeeId = "1004014";
		
		
		List<ScheduleVO> dotSchedulesList = new ScheduleDAO().getDotSchedule(employeeId);
		List<Map<String, Object>> jsonList = new ArrayList<>();

		for (ScheduleVO vo : dotSchedulesList) {
		    Map<String, Object> map = new HashMap<>();
		    map.put("scheduleNo", vo.getScheduleNo());
		    map.put("employeeId", vo.getEmployeeId());
		    map.put("scheduleDate", dateFormat.format(vo.getScheduleDate()));
		    map.put("startTime", timeFormat.format(vo.getStartTime()));
		    map.put("endTime", timeFormat.format(vo.getEndTime()));
		    map.put("title", vo.getTitle());
		    map.put("scheduleType", vo.getScheduleType());
		    jsonList.add(map);
		}
		
		JsonResponse<List<Map<String, Object>>> response = new JsonResponse<>("success", "dot�ҷ�����Ϸ�", jsonList);
		String jsonResponse = CreateJsonResponse.toJson(response);
		request.setAttribute("jsonResponse", jsonResponse);
		return "Json/jsonResult.jsp";

	}

}
