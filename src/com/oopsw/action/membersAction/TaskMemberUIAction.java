package com.oopsw.action.membersAction;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.EmployeeVO;
import com.oopsw.model.MembersDAO;
import com.oopsw.model.ProjectJoinVO;
import com.oopsw.model.TaskDAO;

public class TaskMemberUIAction implements Action {

  private Integer safeParseInt(String s){
    if (s == null) return null;
    s = s.trim();
    if (s.isEmpty() || "undefined".equalsIgnoreCase(s)) return null;
    try { return Integer.valueOf(s); }
    catch(NumberFormatException e){ return null; }
  }

  private String joinCsv(Collection<?> ids){
    if (ids == null || ids.isEmpty()) return "";
    return ids.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  private String normMode(String m){
    if (m == null) return "view";
    m = m.trim().toLowerCase();
    if ("viewtask".equals(m)) return "view";
    if ("edittask".equals(m)) return "edit";
    if ("edit".equals(m)) return "edit";
    return "view";
  }

  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    final String mode   = normMode(request.getParameter("mode"));
    final Integer pno   = safeParseInt(request.getParameter("projectNo"));
    final Integer tno   = safeParseInt(request.getParameter("taskNo"));

    if (pno == null) {
      request.setAttribute("members", Collections.emptyList());
      request.setAttribute("projectMembers", Collections.emptyList());
      request.setAttribute("taskMemberIds", "");
      request.setAttribute("error", "유효하지 않은 프로젝트입니다.");
      return "taskMembersView.jsp";
    }

    if (tno != null) {
      try {
        String taskName = new TaskDAO().selectTaskTitle(tno);
        request.setAttribute("taskName", taskName == null ? "" : taskName);
      } catch (Exception ignore) {}
    }

    request.setAttribute("mode", mode);
    request.setAttribute("projectNo", pno);
    if (tno != null) request.setAttribute("taskNo", tno);

    MembersDAO dao = new MembersDAO();

    if ("view".equals(mode)) {
      List<ProjectJoinVO> members = (tno == null)
          ? Collections.emptyList()
          : dao.selectTaskActiveMembers(tno);
      request.setAttribute("members", members);
      return "taskMembersView.jsp";
    }

    List<ProjectJoinVO> projectMembers = dao.selectActiveProjectMembers(pno);
    request.setAttribute("projectMembers", projectMembers);

    String presetCsv = "";
    if (tno != null) {
      List<Integer> joinedPjoinNos = dao.selectTaskJoinedPjoinNos(tno);
      if (joinedPjoinNos == null) joinedPjoinNos = Collections.emptyList();
      presetCsv = joinCsv(joinedPjoinNos);
    }
    request.setAttribute("taskMemberIds", presetCsv);

    return "taskMembersEdit.jsp";
  }
}
