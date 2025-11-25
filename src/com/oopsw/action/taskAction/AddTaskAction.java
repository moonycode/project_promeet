package com.oopsw.action.taskAction;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.TaskDAO;
import com.oopsw.model.MembersDAO;

public class AddTaskAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    if (s == null || s.getAttribute("user") == null) {
      return "controller?cmd=loginUI";
    }

    int projectNo = Integer.parseInt(request.getParameter("projectNo"));
    String taskName   = request.getParameter("taskName");
    String taskStatus = request.getParameter("taskStatus");
    String startDate  = request.getParameter("startDate");
    String endDate    = request.getParameter("endDate");
    String priority   = request.getParameter("priority");

    Map<String,Object> p = new HashMap<>();
    p.put("projectNo", projectNo);
    p.put("taskName", taskName);
    p.put("taskStatus", taskStatus);
    p.put("startDate", startDate);
    p.put("endDate", endDate);
    p.put("priority", priority);

    TaskDAO tdao = new TaskDAO();
    Integer newTaskNo = tdao.insertTaskAndReturnId(p);

   
    boolean hasPjoinParam = request.getParameterMap().containsKey("pjoinNos");

    String[] pjoinNos = null;
    if (hasPjoinParam) {

      pjoinNos = request.getParameterValues("pjoinNos");

      if (pjoinNos == null || pjoinNos.length == 0) {
        String csv = request.getParameter("pjoinNos");
        if (csv != null && !csv.trim().isEmpty()) {
          pjoinNos = Arrays.stream(csv.split(","))
                           .map(String::trim)
                           .filter(x -> !x.isEmpty())
                           .toArray(String[]::new);
        }
      }
    }

    if (newTaskNo != null && newTaskNo > 0 && hasPjoinParam) {
      new MembersDAO().updateTaskMembers(newTaskNo, pjoinNos);
    }
  
    return "controller?cmd=tasksUI&projectNo=" + projectNo;
  }
}
