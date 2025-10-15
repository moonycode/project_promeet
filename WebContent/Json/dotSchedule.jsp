<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="application/json; charset=UTF-8" language="java" %>
<%@page import="java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap" %>
<%@page import="com.google.gson.Gson, com.oopsw.model.ScheduleVO" %>

<%
    List<ScheduleVO> dotSchedulesList = (List<ScheduleVO>) request.getAttribute("dotSchedulesList");
    List<Map<String, Object>> dotList = new ArrayList<>();
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    for (ScheduleVO vo : dotSchedulesList) {
        Map<String, Object> map = new HashMap<>();
        String scheduleDate = null;
        String startTime = null;
        String endTime = null;

        if (vo.getScheduleDate() != null) {
            scheduleDate = dateFormat.format(vo.getScheduleDate());
        }

        if (vo.getStartTime() != null) {
            startTime = timeFormat.format(vo.getStartTime());
        }

        if (vo.getEndTime() != null) {
            endTime = timeFormat.format(vo.getEndTime());
        }
        
        map.put("schedule_no", vo.getScheduleNo());
        map.put("schedule_date", scheduleDate);
        map.put("schedule_type", vo.getScheduleType());
        map.put("title", vo.getTitle());
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        dotList.add(map);
    }
    Gson gson = new Gson();
    String json = gson.toJson(dotList);
    out.print(json);
%>