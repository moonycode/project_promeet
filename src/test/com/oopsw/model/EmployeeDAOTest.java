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
        assertEquals("ȫ�浿", result.getName());
        assertEquals("����", result.getPosition());
        assertEquals("���", result.getWorkStatus()); 
    }

    /**
     * �α��� ���� (null ��ȯ)
     */
    @Test
    public void loginCheck_InvalidIdTest() throws Exception {
        assertNull(dao.loginCheck("9999999", "1234"));
    }

    /**
     * ��ȿ ID, Ʋ�� PW�� �α��� ���� (null ��ȯ)
     */
    @Test
    public void loginCheck_InvalidPasswordTest() throws Exception {
        assertNull(dao.loginCheck("1004014", "wrongpassword"));
    }

    @Test
    public void goWorkStatusTest() throws Exception {
        assertEquals(1, dao.goWorkStatus("1004014")); 
    }

    /**
     * ���������� ��й�ȣ ���� ���� Ȯ��
     */
    @Test
    public void updatePasswordTest() throws Exception {
        String newPassword = UUID.randomUUID().toString(); // ���ο� �ӽ� ��й�ȣ
      
        assertEquals(1, dao.updatePassword("1004014", newPassword));
        assertEquals(1, dao.updatePassword("1004014", "8fT2kP1q!"));  // �׽�Ʈ ���� ���� ��й�ȣ�� ����
    }
}