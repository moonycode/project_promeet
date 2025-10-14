package com.oopsw.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class CheckListDAO {
	public List<CheckListVO> getChecklist(int taskNo){
		List<CheckListVO> list= null;
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
		list= conn.selectList("detailMapper.getCheckList",taskNo);
		conn.close();
		return  list;
	}

	public int addChecklist(List<CheckListVO> list){
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
		int result = 0;
		for (CheckListVO vo : list) {
			result += conn.insert("detailMapper.addCheckList", vo);
		}
		conn.commit();
		conn.close();
		return result;
	}
	public int updateChecklist(List<CheckListVO> list){
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
		int result = 0;
		for (CheckListVO vo : list) {
			result += conn.update("detailMapper.updateCheckList", vo);
		}
		conn.commit();
		conn.close();
		return result;
	}
	public int deleteChecklist(int checkListNo){
		int result = 0 ;
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
		result = conn.update("detailMapper.deleteCheckList", checkListNo);
		conn.commit();
		conn.close();
		return result;
	}

}
