package com.oopsw.action.taskAction;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.oopsw.action.Action;
import com.oopsw.model.MembersDAO;
import com.oopsw.model.TaskDAO;
import util.CreateJsonResponse;
import util.JsonResponse;

public class UpdateTaskAction implements Action {
  @Override
  public String execute(HttpServletRequest request) throws ServletException, IOException {
    HttpSession s = request.getSession(false);
    if (s == null || s.getAttribute("user") == null) {
      // AJAX라도 미로그인 시 로그인 UI로
      return "controller?cmd=loginUI";
    }

    String json; // 최종 JSON 문자열

    try {
      // 1) 기본 파라미터
      int taskNo    = Integer.parseInt(request.getParameter("taskNo"));
      int projectNo = Integer.parseInt(request.getParameter("projectNo"));

      String taskName   = request.getParameter("taskName");
      String taskStatus = request.getParameter("taskStatus");
      String startDate  = request.getParameter("startDate"); // yyyy-MM-dd 또는 ""
      String endDate    = request.getParameter("endDate");   // yyyy-MM-dd 또는 ""
      String priority   = request.getParameter("priority");

      // 2) 기본정보 업데이트
      Map<String,Object> p = new HashMap<>();
      p.put("taskNo", taskNo);
      p.put("projectNo", projectNo);
      p.put("taskName", taskName);
      p.put("taskStatus", taskStatus);
      p.put("startDate", startDate);
      p.put("endDate", endDate);
      p.put("priority", priority);

      TaskDAO taskDao = new TaskDAO();
      boolean okBasic = taskDao.updateTaskBasicMap(p);

      // 3) 담당자 업데이트 (프런트가 보낼 때만 반영)
      String pjoinStr = request.getParameter("pjoinNos"); // "12,34,57" 또는 null/빈문자열
      if (pjoinStr != null) {
        // 비어있지 않으면 선택된 목록으로 동기화, 비어있으면 "전체 비활성" 시나리오 지원
        String[] arr = Arrays.stream(pjoinStr.split(","))
                             .map(String::trim)
                             .filter(sv -> !sv.isEmpty())
                             .toArray(String[]::new);
        MembersDAO memDao = new MembersDAO();
        memDao.updateTaskMembers(taskNo, arr); // 내부 autoCommit=true
      }
      // ※ 기존 정책(선택했을 때만 전송) 그대로 쓰고 싶으면
      //    위 if(pjoinStr != null) 블록을 if(pjoinStr != null && !pjoinStr.trim().isEmpty()) 로 바꾸세요.

      // 4) 성공 JSON
      JsonResponse<String> body = new JsonResponse<>("success", "OK", null);
      json = CreateJsonResponse.toJson(body);
    } catch (Exception e) {
      // 에러 JSON
      JsonResponse<String> body = new JsonResponse<>("error", e.getMessage(), null);
      json = CreateJsonResponse.toJson(body);
    }

    request.setAttribute("jsonResponse", json);
    return "Json/jsonResult.jsp";
  }
}
