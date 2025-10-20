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
    String pnoStr = request.getParameter("projectNo");

    new TaskDAO().deleteTask(taskNo);

    // projectNo가 없으면 detail에서 복귀용으로 한 번 더 조회할 수도 있지만, 여기선 목록 라우트로
    if (pnoStr == null || pnoStr.isEmpty()) {
      return "controller?cmd=projectUI";
    }
    int projectNo = Integer.parseInt(pnoStr);
    return "controller?cmd=tasksUI&projectNo=" + projectNo;
  }
}
