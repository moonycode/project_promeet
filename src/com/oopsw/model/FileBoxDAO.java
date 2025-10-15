package com.oopsw.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class FileBoxDAO {
    // 프로젝트 목록 조회
    public List<ProjectVO> getProjects() {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<ProjectVO> list = conn.selectList("fileBoxMapper.getProjects");
        conn.close();
        return list;
    }
    
    // 파일 검색 (부분 검색 가능)
    public List<FileBoxVO> searchFiles(String keyword) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<FileBoxVO> list = conn.selectList("fileBoxMapper.searchFiles", keyword);
        conn.close();
        return list;
    }
    
    // 프로젝트 이름 하고 프로젝트 내 업무들의 파일 갯수 조회
    public List<FileBoxVO> getProjectNameTaskFileCount(int projectNo) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<FileBoxVO> list = conn.selectList("fileBoxMapper.getProjectNameTaskFileCount", projectNo);
        conn.close();
        return list;
    }

    // 한 업무 내 파일 목록 조회
    public List<FileBoxVO> getTaskFiles(int taskNo) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<FileBoxVO> list = conn.selectList("fileBoxMapper.getTaskFiles", taskNo);
        conn.close();
        return list;
    }
}
