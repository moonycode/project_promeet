package com.oopsw.model;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.ibatis.session.SqlSession;

public class MembersDAO {

    public List<ProjectJoinVO> selectProjectMembers(int projectNo){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo);
        try (SqlSession s = DBCP.getSqlSessionFactory().openSession()) {
            return s.selectList("membersMapper.selectAllEmployeesWithJoinForProject", p);
        }
    }

    public int syncProjectMembers(int projectNo, List<String> empIds){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo);
        p.put("empIds", (empIds != null) ? empIds : Collections.emptyList());

        SqlSession s = DBCP.getSqlSessionFactory().openSession(false); // ★ 트랜잭션
        try {
            int c1 = 0, c2 = 0, c3 = 0;
            if (!((List<?>)p.get("empIds")).isEmpty()) {
                c1 = s.update("membersMapper.rejoinExistingProjectMembers", p);
                c2 = s.insert("membersMapper.insertNewProjectMembers", p);
            }
            c3 = s.update("membersMapper.deactivateRemovedProjectMembers", p);
            s.commit(); // ★ 한번에 커밋
            return c1 + c2 + c3;
        } catch (RuntimeException e){
            s.rollback();
            throw e;
        } finally {
            s.close();
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

    /** 업무 멤버 저장 (전체 교체 방식) */
    public int updateTaskMembers(int taskNo, String[] pjoinNos){
        // 1) 파라미터 조립
        final List<Integer> ids = (pjoinNos == null) ? Collections.emptyList()
            : Arrays.stream(pjoinNos)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::valueOf)     // ★ 숫자로 변환
                    .collect(Collectors.toList());

        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo);
        p.put("pjoinNos", ids);

        // 2) 트랜잭션으로 일괄 처리
        SqlSession s = DBCP.getSqlSessionFactory().openSession(false); // ★ 트랜잭션
        try {
            int c1 = 0, c2 = 0, c3 = 0;
            if (!ids.isEmpty()) {
                c1 = s.update("membersMapper.rejoinExistingTaskMembers", p);
                c2 = s.insert("membersMapper.insertNewTaskMembers", p);
            }
            c3 = s.update("membersMapper.deactivateRemovedTaskMembers", p);
            s.commit(); // ★ 한번에 커밋
            return c1 + c2 + c3;
        } catch (RuntimeException e){
            s.rollback();
            throw e;
        } finally {
            s.close();
        }
    }
}
