package com.oopsw.model;

import java.util.*;
import org.apache.ibatis.session.SqlSession;

public class TaskDAO {

    public List<TaskVO> selectTasksByProject(Map<String,Object> params){
        SqlSession s = DBCP.getSqlSessionFactory().openSession();
        try{
            return s.selectList("taskMapper.selectTasksByProject", params);
        } finally { s.close(); }
    }

    public boolean insertTask(Map<String,Object> params){
        // mapper는 task_status 컬럼에 #{taskStatus}를 기대하므로, 기본값 '대기' 보정
        if (!params.containsKey("taskStatus") || params.get("taskStatus") == null) {
            params.put("taskStatus", "대기");
        }
        SqlSession s = DBCP.getSqlSessionFactory().openSession(true);
        try{
            int c = s.insert("taskMapper.insertTask", params);
            return c > 0;
        } finally { s.close(); }
    }

    public boolean updateTaskBasicMap(Map<String,Object> params){
        SqlSession s = DBCP.getSqlSessionFactory().openSession(true);
        try{
            int c = s.update("taskMapper.updateTaskBasic", params);
            return c > 0;
        } finally { s.close(); }
    }

    public boolean deleteTask(int taskNo){
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo);
        SqlSession s = DBCP.getSqlSessionFactory().openSession(true);
        try{
            int c = s.update("taskMapper.deleteTask", p);
            return c > 0;
        } finally { s.close(); }
    }


	public TaskVO selectTaskDetail(int taskNo) {
		TaskVO task = null;
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
		task= conn.selectOne("taskMapper.selectTaskDetail",taskNo);
		conn.close();
		return task;
	}
}
