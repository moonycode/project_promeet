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

    ProjectDAO pdao = new ProjectDAO();
    String projectName = pdao.selectProjectTitle(projectNo);

    ProjectVO project = new ProjectVO();
    project.setProjectNo(projectNo);
    project.setProjectName(projectName);
    request.setAttribute("project", project);

    MembersDAO mdao = new MembersDAO();

    // 좌측: 현재 참여 중인 멤버만
    //   => membersMapper.selectActiveProjectMembers 사용
    List<ProjectJoinVO> leftList = mdao.selectActiveProjectMembers(projectNo);

    // 우측: 전체 직원 + joinFlag/managerFlag 정보 포함
    //   => membersMapper.selectAllEmployeesWithJoinForProject 사용
    List<ProjectJoinVO> rightList = mdao.selectProjectMembers(projectNo);

    request.setAttribute("leftList", leftList);
    request.setAttribute("rightList", rightList);

    return "projectMembers.jsp";
  }
}
