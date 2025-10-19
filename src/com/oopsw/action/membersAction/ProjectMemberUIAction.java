package com.oopsw.action.membersAction;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class ProjectMemberUIAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    int projectNo = Integer.parseInt(request.getParameter("projectNo"));
    List<ProjectJoinVO> members = new MembersDAO().selectProjectMembers(projectNo);
    request.setAttribute("members", members);
    request.setAttribute("projectNo", projectNo);
    return "projectMembers.jsp";
  }
}
