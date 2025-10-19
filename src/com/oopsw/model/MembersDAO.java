package com.oopsw.model;

import java.util.*;
import org.apache.ibatis.session.SqlSession;

public class MembersDAO {

    public List<ProjectJoinVO> selectProjectMembers(int projectNo){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo);
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectAllEmployeesWithJoinForProject", p);
        } finally { s.close(); }
    }

    public int syncProjectMembers(int projectNo, List<String> empIds){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo);
        List<String> safe = (empIds != null) ? empIds : Collections.emptyList();
        p.put("empIds", safe);

        try (SqlSession s = DBCP.getSqlSessionFactory().openSession(true)) {
            int c1 = 0, c2 = 0, c3 = 0;
            if (!safe.isEmpty()) {
                c1 = s.update("membersMapper.rejoinExistingProjectMembers", p);
                c2 = s.insert("membersMapper.insertNewProjectMembers", p);
            }
            c3 = s.update("membersMapper.deactivateRemovedProjectMembers", p);
            return c1 + c2 + c3;
        }
    }

    public List<ProjectJoinVO> selectActiveProjectMembers(int projectNo){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo);
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectActiveProjectMembers", p);
        } finally { s.close(); }
    }

    public List<Integer> selectTaskJoinedPjoinNos(int taskNo){
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo);
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectTaskJoinedPjoinNos", p);
        } finally { s.close(); }
    }

    public boolean updateTaskMembers(int taskNo, String[] pjoinNos){
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo);
        List<String> safe = (pjoinNos != null) ? Arrays.asList(pjoinNos) : Collections.emptyList();
        p.put("pjoinNos", safe);

        try (SqlSession s = DBCP.getSqlSessionFactory().openSession(true)) {
            if (!safe.isEmpty()) {
                s.update("membersMapper.rejoinExistingTaskMembers", p);
                s.insert("membersMapper.insertNewTaskMembers", p);
            }
            s.update("membersMapper.deactivateRemovedTaskMembers", p);
            return true;
        }
}
}