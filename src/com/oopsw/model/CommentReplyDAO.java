package com.oopsw.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;



public class CommentReplyDAO {
	public List<CommentReplyVO> getComments(int taskNo){
		List<CommentReplyVO> list= null;
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
		list= conn.selectList("detailMapper.getComments",taskNo);
		conn.close();
		return list;
	}

	public int addComment(CommentVO vo){
		int result = 0;
		SqlSession conn = DBCP.getSqlSessionFactory().openSession();
		result = conn.insert("detailMapper.addComment",vo);
		conn.commit();
		conn.close();
		return result;
	}

	public int updateComment(CommentVO vo) {
		int result = 0;
		SqlSession conn = DBCP.getSqlSessionFactory().openSession();
		result = conn.update("detailMapper.updateComment",vo);
		conn.commit();
		conn.close();
		return result;
	}

	public int deleteComment(int commentNo) {
		int result = 0;
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
		result = conn.update("detailMapper.deleteComment", commentNo);
		conn.commit();
		conn.close();
		return result;
	}

	public int addReply(ReplyVO vo) {
		int result = 0;
		SqlSession conn = DBCP.getSqlSessionFactory().openSession();
		result = conn.insert("detailMapper.addReply",vo);
		conn.commit();
		conn.close();
		return  result;
	}

	public int updateReply(ReplyVO vo) {
		int result = 0;
		SqlSession conn = DBCP.getSqlSessionFactory().openSession();
		result = conn.update("detailMapper.updateReply",vo);
		conn.commit();
		conn.close();
		return result;
	}

	public int deleteReply(int replyNo) {
		int result = 0;
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
		result = conn.update("detailMapper.deleteReply", replyNo);
		conn.commit();
		conn.close();
		return result;
	}

	public int findProjectJoinNo(int taskNo, String employeeId){
		int projectJoinNo = 0 ;
		SqlSession conn=DBCP.getSqlSessionFactory().openSession();
        Map<String, Object> param = new HashMap<>();
        param.put("taskNo", taskNo);
        param.put("employeeId", employeeId);
		projectJoinNo = conn.selectOne("detailMapper.findProjectJoinNo", param );
		conn.close();
		return projectJoinNo;
	}

}
