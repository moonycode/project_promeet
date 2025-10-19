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

    public boolean softDeleteTask(long taskNo){
        SqlSession s = DBCP.getSqlSessionFactory().openSession(true);
        try{
            int c = s.update("taskMapper.softDeleteTask", taskNo);
            return c > 0;
        } finally { s.close(); }
    }
}
