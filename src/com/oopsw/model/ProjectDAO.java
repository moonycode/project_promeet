package com.oopsw.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class ProjectDAO {

    // 진행중 프로젝트 조회
    public List<ProjectVO> getOngoingProjects(String creatorId) {
        SqlSession session = null;
        List<ProjectVO> list = null;
        try {
            session = DBCP.getSqlSessionFactory().openSession();
            list = session.selectList("projectMapper.getOngoingProjects", creatorId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }

    // 완료된 프로젝트 조회
    public List<ProjectVO> getCompletedProjects(String creatorId) {
        SqlSession session = null;
        List<ProjectVO> list = null;
        try {
            session = DBCP.getSqlSessionFactory().openSession();
            list = session.selectList("projectMapper.getCompletedProjects", creatorId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }
    
    // 프로젝트 등록
    public int insertProject(ProjectVO vo, String startDate, String endDate) {
        // 프로젝트 등록과 매니저 참여자 등록을 트랜잭션으로 묶음
        SqlSession session = DBCP.getSqlSessionFactory().openSession();
        
        Map<String, Object> params = new HashMap<>();
        params.put("creatorId", vo.getCreatorId());
        params.put("projectName", vo.getProjectName());
        params.put("client", vo.getClient());
        
        params.put("startDate", startDate); 
        params.put("endDate", endDate);
        params.put("description", vo.getDescription());
        
        int result = 0;
        try {
            // 1. 프로젝트 테이블에 등록
            result = session.insert("projectMapper.insertProject", params);
            
            // 2. 매니저(등록자)를 참여자로 자동 등록
            if (result > 0) {
                
                Map<String, Object> managerParams = new HashMap<>();
                managerParams.put("employee_id", vo.getCreatorId()); 
                managerParams.put("managerFlag", 1); // 매니저 플래그 설정
                
                int joinResult = session.insert("projectMapper.insertProjectManager", managerParams);
                
                if (joinResult > 0) {
                    session.commit(); // 두 쿼리가 모두 성공하면 커밋
                } else {
                    session.rollback(); // 매니저 등록 실패 시 롤백
                    result = 0; // 최종 결과 실패 처리
                }
            } else {
                session.rollback();
            }
        } catch (Exception e) {
            session.rollback(); // 오류 발생 시 롤백
            e.printStackTrace();
            result = 0; // 실패 결과 반환
        } finally {
            session.close();
        }
        return result;
    }
    
    //프로젝트 선택
    public ProjectVO getProjectByNo(int projectNo) {
        SqlSession session = null;
        ProjectVO project = null;
        try {
            session = DBCP.getSqlSessionFactory().openSession();
            project = session.selectOne("projectMapper.getProjectByNo", projectNo); 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return project;
    }
 
    
    // 프로젝트 수정
    public int updateProject(ProjectVO vo, String startDate, String endDate) {
        SqlSession session = DBCP.getSqlSessionFactory().openSession();
        int result = 0;
        
        Map<String, Object> params = new HashMap<>();
        params.put("projectNo", vo.getProjectNo());
        params.put("projectName", vo.getProjectName());
        params.put("client", vo.getClient());
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("description", vo.getDescription());

        try {
            result = session.update("projectMapper.updateProject", params);
            if (result > 0) {
                session.commit();
            } else {
                session.rollback();
            }
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    
    // 프로젝트 종료(완료)
    public int completeProject(int projectNo) {
        SqlSession session = DBCP.getSqlSessionFactory().openSession();
        int result = 0;
        try {
            result = session.update("projectMapper.completeProject", projectNo);
            if (result > 0) {
                session.commit();
            } else {
                session.rollback();
            }
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    
    // 프로젝트 삭제(휴지통으로 이동)
    public int deleteProject(int projectNo) {
        SqlSession session = DBCP.getSqlSessionFactory().openSession();
        int result = 0;
        try {
            result = session.update("projectMapper.deleteProject", projectNo);
            if (result > 0) {
                session.commit();
            } else {
                session.rollback();
            }
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    
    // 삭제된 프로젝트(휴지통) 조회
    public List<ProjectVO> getBinProjects(String creatorId) {
        SqlSession session = null;
        List<ProjectVO> list = null;
        try {
            session = DBCP.getSqlSessionFactory().openSession();
            list = session.selectList("projectMapper.getBinProjects", creatorId); 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return list;
    }
    
    // 복원할 프로젝트 선택
    public ProjectVO selectBinProject(int projectNo) {
        SqlSession session = null;
        ProjectVO vo = null;
        try {
            session = DBCP.getSqlSessionFactory().openSession();
            vo = session.selectOne("projectMapper.selectBinProject", projectNo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return vo;
    }
    
    // 프로젝트 복원
    public int restoreProject(int projectNo) {
        SqlSession session = DBCP.getSqlSessionFactory().openSession();
        int result = 0;
        try {
            result = session.update("projectMapper.restoreProject", projectNo);
            if (result > 0) {
                session.commit();
            } else {
                session.rollback();
            }
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

}