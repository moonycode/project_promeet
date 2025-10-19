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
    String[] ids    = request.getParameterValues("employeeId"); // 사번 목록
    String[] flags  = request.getParameterValues("joinFlag");   // "1"/"0"

    // 설명: 체크된(또는 참여로 표시된) 사번만 수집 → 집합 동기화
    List<String> selected = new ArrayList<>();
    if (ids != null && flags != null && ids.length == flags.length) {
      for (int i = 0; i < ids.length; i++) {
        if ("1".equals(flags[i])) selected.add(ids[i]);
      }
    }

    MembersDAO dao = new MembersDAO();
    dao.syncProjectMembers(projectNo, selected); // XML: rejoin → insert → deactivate

    return "controller?cmd=updateProjectUI&projectNo=" + projectNo;
  }
}
