package com.oopsw.action.taskAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class AddTaskAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    String projectNoStr = request.getParameter("projectNo");
    String taskName     = request.getParameter("taskName");
    String priority     = request.getParameter("priority");
    String startDate    = request.getParameter("startDate");
    String endDate      = request.getParameter("endDate");

    long projectNo = Long.parseLong(projectNoStr);

    Map<String,Object> p = new HashMap<>();
    p.put("projectNo", projectNo);
    p.put("taskName", taskName);
    p.put("priority", priority);
    p.put("startDate", startDate);
    p.put("endDate", endDate);

    new TaskDAO().insertTask(p);

    return "controller?cmd=taskUI&projectNo=" + projectNo;
  }
}
