package test.com.oopsw.model;

import static org.junit.Assert.*;

import java.util.UUID; // ��й�ȣ ���� �׽�Ʈ�� ���� ���� ���ڿ� ������

import org.junit.Before;
import org.junit.Test;

import com.oopsw.model.EmployeeDAO;
import com.oopsw.model.EmployeeVO;

public class EmployeeDAOTest {
    static EmployeeDAO dao;

    @Before
    public void start() {
        dao = new EmployeeDAO();
    }

    @Test
    public void loginCheck_SuccessTest() throws Exception {
        EmployeeVO result = dao.loginCheck("1004014", "8fT2kP1q!");
        
        assertNotNull(result); 
        assertEquals("1004014", result.getEmployeeId()); 
        assertEquals("홍길동", result.getName());
        assertEquals("과장", result.getPosition());
        assertEquals("출근", result.getWorkStatus()); 
    }


    @Test
    public void loginCheck_InvalidIdTest() throws Exception {
        assertNull(dao.loginCheck("9999999", "1234"));
    }


    @Test
    public void loginCheck_InvalidPasswordTest() throws Exception {
        assertNull(dao.loginCheck("1004014", "wrongpassword"));
    }

    @Test
    public void goWorkStatusTest() throws Exception {
        assertEquals(1, dao.goWorkStatus("1004014")); 
    }


    @Test
    public void updatePasswordTest() throws Exception {
    	String currentPassword = "8fT2kP1q!";
        String newPassword = "1111";
      
        assertEquals(1, dao.updatePassword("1004014", currentPassword, newPassword));
        assertEquals(1, dao.updatePassword("1004014", "1111" ,"8fT2kP1q!"));
    }
}