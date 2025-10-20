// src/com/oopsw/action/taskAction/TasksUIAction.java
package com.oopsw.action.taskAction;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.*;

public class TasksUIAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    EmployeeVO user = (s != null) ? (EmployeeVO) s.getAttribute("user") : null;
    if (user == null) return "controller?cmd=loginUI";

    String p = request.getParameter("projectNo");
    if (p == null) {
      Object attr = request.getAttribute("projectNo");
      if (attr != null) p = String.valueOf(attr);
    }

    int projectNo = (p != null && p.matches("\\d+")) ? Integer.parseInt(p) : -1;
    if (projectNo <= 0) {
      request.setAttribute("errorMsg", "유효하지 않은 프로젝트 번호입니다.");
      return "controller?cmd=projectsUI";
    }
    System.out.println("[TasksUI] projectNo=" + projectNo + ", ctx=" + request.getContextPath());


    ProjectDAO pdao = new ProjectDAO();
    ProjectVO project = pdao.selectProjectHeader(projectNo); // 기존 헤더 조회 메서드 그대로 사용
    if (project == null) return "controller?cmd=projectsUI";

    boolean isCompleted = (project.getCompleteDate() != null);
    boolean isDeleted   = (project.getDelDate() != null);
    boolean readonly    = (isCompleted || isDeleted);

    // ⭐ 삭제/완료 프로젝트면 includeDeleted=1 → 삭제된 업무까지 모두 조회
    Map<String,Object> params = new HashMap<>();
    params.put("projectNo", projectNo);
    params.put("taskStatus", opt(request.getParameter("taskStatus"))); // null/ALL 처리 그대로
    params.put("priority",   opt(request.getParameter("priority")));
    params.put("orderBy",    opt(request.getParameter("orderBy")));
    params.put("includeDeleted", (readonly ? 1 : 0));

    TaskDAO tdao = new TaskDAO();
    List<TaskVO> tasks = tdao.selectTasksByProject(params);

    request.setAttribute("project", project);
    request.setAttribute("tasks", tasks);
    request.setAttribute("readonly", readonly);
    request.setAttribute("isDeletedProject", isDeleted);
    request.setAttribute("isCompletedProject", isCompleted);

    return "/tasks.jsp"; 
  }

  private String opt(String v){
    return (v == null || v.trim().isEmpty()) ? null : v.trim();
  }
}
