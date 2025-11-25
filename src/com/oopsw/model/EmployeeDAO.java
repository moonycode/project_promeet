package com.oopsw.model;

import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

public class EmployeeDAO {

    public EmployeeVO loginCheck(String employeeId, String password) throws Exception {
        try (SqlSession session = DBCP.getSqlSessionFactory().openSession()) {
            Map<String, String> params = new HashMap<>();
            params.put("employeeId", employeeId);
            params.put("password", password);
            
            return session.selectOne("employeeMapper.loginCheck", params); 
        }
    }
    

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
    
    public int updateWorkStatus(String employeeId, String workStatus){
        try (SqlSession s = DBCP.getSqlSessionFactory().openSession()) {
            Map<String,Object> p = new HashMap<>();
            p.put("employeeId", employeeId);
            p.put("workStatus", workStatus);
            int c = s.update("employeeMapper.updateWorkStatus", p);
            s.commit();
            return c;
        }
    }

    
    public EmployeeVO getEmployeeInfo(String employeeId) {
        SqlSession session = DBCP.getSqlSessionFactory().openSession();
        EmployeeVO employeeVO = null;
        try {
            employeeVO = session.selectOne("employeeMapper.getEmployeeInfo", employeeId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeVO;
    }


    public int updatePassword(String employeeId, String currentPassword, String newPassword) throws Exception {
        SqlSession session = DBCP.getSqlSessionFactory().openSession();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("employeeId", employeeId);
            params.put("currentPassword", currentPassword);
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