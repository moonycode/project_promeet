package com.oopsw.model;

import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

public class EmployeeDAO {
    // �α��� üũ �� ����� ���� ��ȸ
    public EmployeeVO loginCheck(String employeeId, String password) throws Exception {
        // SELECT�� openSession()�� ����ϸ� �ڵ� Ŀ�� ���
        try (SqlSession session = DBCP.getSqlSessionFactory().openSession()) {
            Map<String, String> params = new HashMap<>();
            params.put("employeeId", employeeId);
            params.put("password", password);
            
            return session.selectOne("employeeMapper.loginCheck", params); 
        }
    }
    
    // �α��� �� ���º���(���)
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

    // ���������� ��й�ȣ ����
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