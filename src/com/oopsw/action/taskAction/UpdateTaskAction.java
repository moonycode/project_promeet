package com.oopsw.action.taskAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class UpdateTaskAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    int taskNo    = Integer.parseInt(request.getParameter("taskNo"));
    int projectNo = Integer.parseInt(request.getParameter("projectNo"));

    Map<String,Object> p = new HashMap<>();
    p.put("taskNo", taskNo);
    p.put("taskName",  request.getParameter("taskName"));
    p.put("taskStatus",request.getParameter("taskStatus"));
    p.put("priority",  request.getParameter("priority"));
    p.put("startDate", request.getParameter("startDate"));
    p.put("endDate",   request.getParameter("endDate"));

    new TaskDAO().updateTaskBasicMap(p);
    return "controller?cmd=taskUI&projectNo=" + projectNo;
  }
}
