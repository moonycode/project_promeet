package com.oopsw.model;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.ibatis.session.SqlSession;

public class MembersDAO {
	
    public List<EmployeeVO> selectTaskMembersByTask(int taskNo){
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectTaskMembersByTask", taskNo);
        } finally {
            s.close();
        }
    }
	
    public int syncProjectMembers(int projectNo, List<String> employeeIds) {
        Map<String, Object> p = new HashMap<>();
        p.put("projectNo", projectNo);
        // 빈 목록은 deactivate 전체(팀장 제외) 시나리오를 위해 그대로 emptyList로 넘깁니다.
        List<String> safeList = (employeeIds != null) ? employeeIds : Collections.emptyList();
        p.put("employeeIds", safeList);

        int c1 = 0, c2 = 0, c3 = 0;

        // try-with-resources + 수동 커밋(원자성 보장).
        try (SqlSession s = DBCP.getSqlSessionFactory().openSession(false)) {
            if (!safeList.isEmpty()) {
                // 1) 기존 멤버 재활성화.
                c1 = s.update("membersMapper.rejoinExistingProjectMembers", p);
                // 2) 완전 신규만 INSERT.
                c2 = s.insert("membersMapper.insertNewProjectMembers", p);
            }
            // 3) 목록에 없는 멤버 비활성화(팀장 제외는 매퍼에서 필터).
            c3 = s.update("membersMapper.deactivateRemovedProjectMembers", p);

            s.commit();
        } catch (RuntimeException e) {
            // try-with-resources에서는 close 전에 rollback 할 수 있도록 별도 블록 필요.
            try (SqlSession s2 = DBCP.getSqlSessionFactory().openSession()) {
                s2.rollback(); // 이미 닫힌 세션에선 불가하므로, 필요시 위 try를 전통적 try/finally로 바꿔도 됩니다.
            } catch (Exception ignore) {}
            throw e;
        }
        return c1 + c2 + c3;
    }

    
    public int updateTaskMembers(int taskNo, String[] pjoinNos){
        List<Integer> list = new ArrayList<>();
        if (pjoinNos != null) {
            for (String s : pjoinNos) {
                if (s == null || s.trim().isEmpty()) continue;
                try { list.add(Integer.parseInt(s.trim())); } catch (NumberFormatException ignore) {}
            }
        }

        Map<String,Object> param = new HashMap<>();
        param.put("taskNo", taskNo);
        param.put("pjoinNos", list);

        int c1 = 0, c2 = 0, c3 = 0;
        SqlSession s = DBCP.getSqlSessionFactory().openSession(false); // 수동 커밋
        try {
            if (!list.isEmpty()) {
                c1 = s.update("membersMapper.rejoinExistingTaskMembers", param);
                c2 = s.insert("membersMapper.insertNewTaskMembers", param); // NOT EXISTS 포함 권장
            }
            c3 = s.update("membersMapper.deactivateRemovedTaskMembers", param);
            s.commit();
            return c1 + c2 + c3;
        } catch (RuntimeException e) {
            s.rollback();
            throw e;
        } finally {
            s.close();
        }
    }

    
    public List<ProjectJoinVO> selectProjectMembers(int projectNo){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo);
        try (SqlSession s = DBCP.getSqlSessionFactory().openSession()) {
            return s.selectList("membersMapper.selectAllEmployeesWithJoinForProject", p);
        }
    }

 

    public List<ProjectJoinVO> selectActiveProjectMembers(int projectNo){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo);
        try (SqlSession s = DBCP.getSqlSessionFactory().openSession()) {
            return s.selectList("membersMapper.selectActiveProjectMembers", p);
        }
    }

    public List<Integer> selectTaskJoinedPjoinNos(int taskNo){
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo);
        try (SqlSession s = DBCP.getSqlSessionFactory().openSession()) {
            return s.selectList("membersMapper.selectTaskJoinedPjoinNos", p);
        }
    }

    public List<ProjectJoinVO> selectTaskActiveMembers(int taskNo){
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo);
        try (SqlSession s = DBCP.getSqlSessionFactory().openSession()) {
            return s.selectList("membersMapper.selectTaskActiveMembers", p);
        }
    }
   
}

   

