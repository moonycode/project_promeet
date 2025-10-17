package com.oopsw.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class FileBoxDAO {
    public List<ProjectVO> getProjects() {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<ProjectVO> list = conn.selectList("fileBoxMapper.getProjects");
        conn.close();
        return list;
    }
    
    public List<SearchFileBoxVO> searchFilesOrTask(String keyword, Integer taskNo) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        Map<String, Object> param = new HashMap<>();
        param.put("searchKeyword", keyword);
        param.put("taskNo", taskNo);

        List<SearchFileBoxVO> list = conn.selectList("fileBoxMapper.searchFilesOrTask", param);
        conn.close();
        return list;
    }
    
      public List<ProjectFileBoxVO> getProjectNameTaskFileCount(int projectNo) {
        SqlSession conn = DBCP.getSqlSessionFactory().openSession();
        List<ProjectFileBoxVO> list = conn.selectList("fileBoxMapper.getProjectNameTaskFileCount", projectNo);
        conn.close();
        return list;
    }

}
