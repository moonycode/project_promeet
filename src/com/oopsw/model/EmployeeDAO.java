package com.oopsw.model;

import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

public class EmployeeDAO {
    // 로그인 체크 및 사용자 정보 조회
    public EmployeeVO loginCheck(String employeeId, String password) throws Exception {
        // SELECT는 openSession()만 사용하면 자동 커밋 모드
        try (SqlSession session = DBCP.getSqlSessionFactory().openSession()) {
            Map<String, String> params = new HashMap<>();
            params.put("employeeId", employeeId);
            params.put("password", password);
            
            return session.selectOne("employeeMapper.loginCheck", params); 
        }
    }
    
    // 로그인 시 상태변경(출근)
    public int goWorkStatus(String employeeId) throws Exception {
        SqlSession session = DBCP.getSqlSessionFactory().openSession();
        try {
            int result = session.update("employeeMapper.goWorkStatus", employeeId);
            if (result > 0) {
                session.commit();
            } else {
                session.rollback();
            }
            return result;
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    // 마이페이지 비밀번호 변경
    public int updatePassword(String employeeId, String newPassword) throws Exception {
        SqlSession session = DBCP.getSqlSessionFactory().openSession(false);
        try {
            Map<String, String> params = new HashMap<>();
            params.put("employeeId", employeeId);
            params.put("newPassword", newPassword);
            
            int result = session.update("employeeMapper.updatePassword", params);
            
            if (result > 0) {
                session.commit();
            } else {
                session.rollback();
            }
            return result;
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
}