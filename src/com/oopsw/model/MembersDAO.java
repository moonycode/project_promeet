package com.oopsw.model;

import java.util.*;
import org.apache.ibatis.session.SqlSession;

public class MembersDAO {

    public List<ProjectJoinVO> selectProjectMembers(long projectNo){
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectProjectMembers", projectNo);
        } finally { s.close(); }
    }

    public boolean upsertProjectJoin(long projectNo, long employeeId, int joinFlag){
        SqlSession s = DBCP.getSqlSessionFactory().openSession(true);
        try{
            Map<String,Object> p = new HashMap<>();
            p.put("projectNo", projectNo);
            p.put("employeeId", employeeId);
            p.put("joinFlag", joinFlag);
            int c = s.update("membersMapper.upsertProjectJoin", p);
            return c > 0;
        } finally { s.close(); }
    }

    public List<ProjectJoinVO> selectActiveProjectMembers(long projectNo){
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectActiveProjectMembers", projectNo);
        } finally { s.close(); }
    }

    public List<Long> selectTaskJoinedPjoinNos(long taskNo){
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectTaskJoinedPjoinNos", taskNo);
        } finally { s.close(); }
    }

    public boolean updateTaskMembers(long taskNo, String[] pjoinNos){
        SqlSession s = DBCP.getSqlSessionFactory().openSession(true);
        try{
            Map<String,Object> p = new HashMap<>();
            p.put("taskNo", taskNo);
            p.put("pjoinNos", pjoinNos != null ? Arrays.asList(pjoinNos) : Collections.emptyList());
            int c = s.update("membersMapper.updateTaskMembers", p);
            return c >= 0;
        } finally { s.close(); }
    }
}
