package com.oopsw.model;

import java.util.*;
import org.apache.ibatis.session.SqlSession;

public class TaskDAO {

    /** 단일 제목 조회 */
    public String selectTaskTitle(int taskNo){
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        try {
            return conn.selectOne("taskMapper.selectTaskTitle", taskNo);
        } finally {
            conn.close();
        }
    }

    /** 프로젝트별 업무 목록 조회 */
    public List<TaskVO> selectTasksByProject(Map<String,Object> params){
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        try {
            return conn.selectList("taskMapper.selectTasksByProject", params);
        } finally {
            conn.close();
        }
    }

    /**
     * 업무 등록 후 PK 반환
     * (mapper에서 useGeneratedKeys 또는 <selectKey>로 taskNo 세팅되어 있어야 합니다)
     */
    public Integer insertTaskAndReturnId(Map<String,Object> params){
        if (!params.containsKey("taskStatus") || params.get("taskStatus") == null) params.put("taskStatus", "대기");
        if (!params.containsKey("priority")   || params.get("priority")   == null) params.put("priority",   "없음");

        SqlSession conn = DBCP.getSqlSessionFactory().openSession(false); // 수동 커밋
        try {
            int c = conn.insert("taskMapper.insertTask", params);
            if (c > 0) {
                conn.commit();
                Object id = params.get("taskNo");
                if (id instanceof Integer) return (Integer) id;
                if (id != null) return Integer.parseInt(String.valueOf(id));
                return null;
            } else {
                conn.rollback();
                return null;
            }
        } catch (RuntimeException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    /** 업무 기본정보(Map) 업데이트 */
    public boolean updateTaskBasicMap(Map<String,Object> params){
        SqlSession conn = DBCP.getSqlSessionFactory().openSession(false);
        try {
            int c = conn.update("taskMapper.updateTaskBasic", params);
            if (c > 0) conn.commit(); else conn.rollback();
            return c > 0;
        } catch (RuntimeException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    /** 업무 기본정보(개별 파라미터) 업데이트 — 내부적으로 Map 버전 호출 */
    public int updateTask(int taskNo, int projectNo,
                          String taskName, String taskStatus,
                          String startDate, String endDate,
                          String priority) {
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo);
        p.put("projectNo", projectNo);
        p.put("taskName", taskName);
        p.put("taskStatus", taskStatus);
        p.put("startDate", startDate);
        p.put("endDate", endDate);
        p.put("priority", priority);
        return updateTaskBasicMap(p) ? 1 : 0;
    }

    /** 업무 논리삭제 */
    public boolean deleteTask(int taskNo){
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo);

        SqlSession conn = DBCP.getSqlSessionFactory().openSession(false);
        try {
            int c = conn.update("taskMapper.deleteTask", p);
            if (c > 0) conn.commit(); else conn.rollback();
            return c > 0;
        } catch (RuntimeException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    /** 업무 상세 조회 */
    public TaskVO selectTaskDetail(int taskNo) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        try {
            return conn.selectOne("taskMapper.selectTaskDetail", taskNo);
        } finally {
            conn.close();
        }
    }
}
