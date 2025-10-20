// src/com/oopsw/action/membersAction/UpdateTaskMemberAction.java
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

    // 1) 반복 파라미터 우선: pjoinNos=1&pjoinNos=2...
    String[] pjoinNos = request.getParameterValues("pjoinNos");

    // 2) 과거 호환: pjoinNo=1&pjoinNo=2...
    if (pjoinNos == null || pjoinNos.length == 0) {
      pjoinNos = request.getParameterValues("pjoinNo");
    }

    // 3) 콤마 문자열 호환: pjoinNos="1,2,3"
    if ((pjoinNos == null || pjoinNos.length == 0)) {
      String csv = request.getParameter("pjoinNos");
      if (csv != null && !csv.trim().isEmpty()) {
        pjoinNos = Arrays.stream(csv.split(","))
                .map(String::trim).filter(sv -> !sv.isEmpty())
                .toArray(String[]::new);
      }
    }

    new MembersDAO().updateTaskMembers(taskNo, pjoinNos);

    // ★ 라우팅은 항상 tasksUI(복수)
    return "controller?cmd=tasksUI&projectNo=" + projectNo;
  }
}
