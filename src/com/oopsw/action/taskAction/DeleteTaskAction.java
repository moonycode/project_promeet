package com.oopsw.action.taskAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class DeleteTaskAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    long taskNo    = Long.parseLong(request.getParameter("taskNo"));
    long projectNo = Long.parseLong(request.getParameter("projectNo"));

    new TaskDAO().softDeleteTask(taskNo);
    return "controller?cmd=taskUI&projectNo=" + projectNo;
  }
}
