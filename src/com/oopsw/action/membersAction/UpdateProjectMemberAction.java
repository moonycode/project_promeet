package com.oopsw.action.membersAction;

import java.io.IOException;
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

    int projectNo  = Integer.parseInt(request.getParameter("projectNo"));
    String[] ids    = request.getParameterValues("employeeId");
    String[] flags  = request.getParameterValues("joinFlag");

    MembersDAO dao = new MembersDAO();
    if (ids != null && flags != null && ids.length == flags.length){
      for (int i=0; i<ids.length; i++){
        String empId = ids[i];
        int  flag  = Integer.parseInt(flags[i]);
        dao.upsertProjectJoin(projectNo, empId, flag);
      }
    }
    return "controller?cmd=updateProjectUI&projectNo=" + projectNo;
  }
}
