package com.oopsw.action.taskAction;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class TasksUIAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    String projectNoStr = request.getParameter("projectNo");
    if (projectNoStr == null || projectNoStr.isEmpty()) return "controller?cmd=projectUI";
    long projectNo = Long.parseLong(projectNoStr);

    String taskStatus = request.getParameter("taskStatus");
    if (taskStatus == null || taskStatus.isEmpty()) taskStatus = "ALL";

    String priority = request.getParameter("priority");
    if (priority == null || priority.isEmpty()) priority = "ALL";

    String orderBy = request.getParameter("orderBy");
    if (orderBy == null) orderBy = "";

    ProjectVO header = new ProjectDAO().selectProjectHeader(projectNo);

    Map<String,Object> p = new HashMap<>();
    p.put("projectNo", projectNo);
    p.put("taskStatus", taskStatus);
    p.put("priority",   priority);
    p.put("orderBy",    orderBy);

    request.setAttribute("project", header);
    request.setAttribute("tasks", new TaskDAO().selectTasksByProject(p));

    return "tasks.jsp";
  }
}
