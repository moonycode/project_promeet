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

    int taskNo    = Integer.parseInt(request.getParameter("taskNo"));
    int projectNo = Integer.parseInt(request.getParameter("projectNo"));

    new TaskDAO().deleteTask(taskNo);
    return "controller?cmd=taskUI&projectNo=" + projectNo;
  }
}
