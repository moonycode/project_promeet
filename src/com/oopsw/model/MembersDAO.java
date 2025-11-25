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

        List<String> keepList = (employeeIds != null) ? employeeIds : Collections.emptyList();
        p.put("employeeIds", keepList);

        int c1 = 0, c2 = 0, c3 = 0, c4 = 0;

        // 수동 커밋 + 동일 세션에서 롤백 처리
        SqlSession s = DBCP.getSqlSessionFactory().openSession(false);
        try {
            if (!keepList.isEmpty()) {
                // 1) 기존 멤버 재활성화
                c1 = s.update("membersMapper.rejoinExistingProjectMembers", p);
                // 2) 완전 신규만 INSERT
                c2 = s.insert("membersMapper.insertNewProjectMembers", p);
            }

            // 3) (NEW) 이번에 제외되는 사람들의 '모든 업무 담당' OFF
            //    keepList에 없는 인원 대상. keepList가 비어있으면 비관리자 전원 OFF.
            c3 = s.update("membersMapper.deactivateTaskMembersByRemovedEmployees", p);

            // 4) 프로젝트 참여 자체 OFF (비관리자)
            c4 = s.update("membersMapper.deactivateRemovedProjectMembers", p);

            s.commit();
            return c1 + c2 + c3 + c4;
        } catch (RuntimeException e) {
            s.rollback();
            throw e;
        } finally {
            s.close();
        }
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

   

