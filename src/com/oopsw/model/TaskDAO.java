package com.oopsw.model;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

public class TaskDAO {

	public List<TaskVO> selectTasksByProject(Map<String,Object> params){
		SqlSession s = DBCP.getSqlSessionFactory().openSession();
		try{
			return s.selectList("taskMapper.selectTasksByProject", params);
		} finally { s.close(); }
	}

	public boolean insertTask(Map<String,Object> params){
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
		SqlSession s = DBCP.getSqlSessionFactory().openSession(true);
		try{
			int c = s.update("taskMapper.deleteTask", taskNo);
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
