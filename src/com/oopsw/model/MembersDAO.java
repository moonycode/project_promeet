package com.oopsw.model;

import java.util.*;
import org.apache.ibatis.session.SqlSession;

public class MembersDAO {

    public List<ProjectJoinVO> selectProjectMembers(int projectNo){
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectProjectMembers", projectNo);
        } finally { s.close(); }
    }

    /** employeeId는 샘플데이터가 문자열 사번이므로 String */
    public boolean upsertProjectJoin(int projectNo, String employeeId, int joinFlag){
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

    public List<ProjectJoinVO> selectActiveProjectMembers(int projectNo){
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectActiveProjectMembers", projectNo);
        } finally { s.close(); }
    }

    /** pjoin_no 컬럼 int로 받을게요 */
    public List<Integer> selectTaskJoinedPjoinNos(int taskNo){
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("membersMapper.selectTaskJoinedPjoinNos", taskNo);
        } finally { s.close(); }
    }

    public boolean updateTaskMembers(int taskNo, String[] pjoinNos){
        SqlSession s = DBCP.getSqlSessionFactory().openSession(true);
        try{
            Map<String,Object> p = new HashMap<>();
            p.put("taskNo", taskNo);
            p.put("pjoinNos", pjoinNos != null ? Arrays.asList(pjoinNos) : Collections.emptyList());
            int c = s.update("membersMapper.updateTaskMembers", p);
            return c >= 0; // 멱등 보장 형태 유지
        } finally { s.close(); }
    }
}
