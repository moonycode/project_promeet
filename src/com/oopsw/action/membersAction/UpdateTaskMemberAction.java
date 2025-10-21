package com.oopsw.action.membersAction;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeVO;
import com.oopsw.model.MembersDAO;

public class UpdateTaskMemberAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    int projectNo = Integer.parseInt(request.getParameter("projectNo"));
    int taskNo    = Integer.parseInt(request.getParameter("taskNo"));

    String[] pjoinNos = request.getParameterValues("pjoinNos");
    if (pjoinNos == null || pjoinNos.length == 0) pjoinNos = request.getParameterValues("pjoinNo");
    if (pjoinNos == null || pjoinNos.length == 0) {
      String csv = request.getParameter("pjoinNos");
      if (csv != null && !csv.trim().isEmpty()) {
        pjoinNos = Arrays.stream(csv.split(",")).map(String::trim).filter(x->!x.isEmpty()).toArray(String[]::new);
      }
    }

    new MembersDAO().updateTaskMembers(taskNo, pjoinNos);
    return "controller?cmd=tasksUI&projectNo=" + projectNo;
  }
}
