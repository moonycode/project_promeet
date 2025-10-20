package com.oopsw.action.membersAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class UpdateTaskMemberAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    int projectNo = Integer.parseInt(request.getParameter("projectNo"));
    int taskNo    = Integer.parseInt(request.getParameter("taskNo"));

    String[] pjoinNos = request.getParameterValues("pjoinNo");
    new MembersDAO().updateTaskMembers(taskNo, pjoinNos);

    return "controller?cmd=taskUI&projectNo=" + projectNo;
  }
}
