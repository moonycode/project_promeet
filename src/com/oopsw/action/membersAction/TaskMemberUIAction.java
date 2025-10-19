package com.oopsw.action.membersAction;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class TaskMemberUIAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    int projectNo = Integer.parseInt(request.getParameter("projectNo"));
    int taskNo    = Integer.parseInt(request.getParameter("taskNo"));

    MembersDAO dao = new MembersDAO();
    List<ProjectJoinVO> candidates = dao.selectActiveProjectMembers(projectNo);
    List<Integer> checkedPjoinNos = dao.selectTaskJoinedPjoinNos(taskNo);

    request.setAttribute("projectNo", projectNo);
    request.setAttribute("taskNo", taskNo);
    request.setAttribute("candidates", candidates);
    request.setAttribute("checkedPjoinNos", checkedPjoinNos);

    return "Jspf/taskMembersModal.jspf";
  }
}
