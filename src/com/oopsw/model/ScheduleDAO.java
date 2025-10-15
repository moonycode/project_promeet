package com.oopsw.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class ScheduleDAO {
	
	public List<ScheduleVO> getDotSchedule(String employeeId) {
		   List<ScheduleVO> dotSchedulesList = new ArrayList<>();
			SqlSession conn=DBCP.getSqlSessionFactory().openSession();
			dotSchedulesList= conn.selectList("scheduleMapper.getDotSchedule", employeeId);
			conn.close();
		   
		return dotSchedulesList;
	}

	public boolean addSchedule(ScheduleVO scheduleVO) {
	    SqlSession conn = DBCP.getSqlSessionFactory().openSession();
	    boolean result = false;
	    try {
	        int count = conn.insert("scheduleMapper.addSchedule", scheduleVO);
	        conn.commit();
	        result = count > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        result = false;
	    } finally {
	        conn.close();
	    }
	    return result;
	}

	public boolean deleteSchedule(int scheduleNo) {
	    SqlSession conn = DBCP.getSqlSessionFactory().openSession();
	    boolean result = false;
	    try {
	        int count = conn.insert("scheduleMapper.deleteSchedule", scheduleNo);
	        conn.commit();
	        result = count > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        result = false;
	    } finally {
	        conn.close();
	    }
	    return result;
	}


	public boolean updateSchedule(ScheduleVO scheduleVO) {
	    SqlSession conn = DBCP.getSqlSessionFactory().openSession();
	    boolean result = false;
	    try {
	        int count = conn.insert("scheduleMapper.updateSchedule", scheduleVO);
	        conn.commit();
	        result = count > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        result = false;
	    } finally {
	        conn.close();
	    }
	    return result;
	}

}
