package com.oopsw.action.membersAction;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class UpdateProjectMemberAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    int projectNo   = Integer.parseInt(request.getParameter("projectNo"));

    String csv = request.getParameter("employeeIds");
    List<String> selected = new ArrayList<>();
    if (csv != null && !csv.trim().isEmpty()) {
      for (String id : csv.split(",")) {
        String v = id.trim();
        if (!v.isEmpty()) selected.add(v);
      }
    }

    MembersDAO dao = new MembersDAO();
    dao.syncProjectMembers(projectNo, selected);

    return "controller?cmd=tasksUI&projectNo=" + projectNo;
  }
}
