package com.oopsw.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class FileBoxDAO {
    public List<ProjectVO> getProjects() {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<ProjectVO> list = conn.selectList("fileBoxMapper.getProjects");
        conn.close();
        return list;
    }
    
    public List<SearchFileBoxVO> searchFiles(String keyword) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<SearchFileBoxVO> list = conn.selectList("fileBoxMapper.searchFiles", keyword);
        conn.close();
        return list;
    }
    
      public List<ProjectFileBoxVO> getProjectNameTaskFileCount(int projectNo) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<ProjectFileBoxVO> list = conn.selectList("fileBoxMapper.getProjectNameTaskFileCount", projectNo);
        conn.close();
        return list;
    }

      public List<FilesTaskFileBoxVO> getTaskFiles(int taskNo) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<FilesTaskFileBoxVO> list = conn.selectList("fileBoxMapper.getTaskFiles", taskNo);
        conn.close();
        return list;
    }
}
