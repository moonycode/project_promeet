package test.com.oopsw.model;

import static org.junit.Assert.*;

import java.util.UUID; // 비밀번호 변경 테스트를 위한 랜덤 문자열 생성용

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

    /**
     * 로그인 실패 (null 반환)
     */
    @Test
    public void loginCheck_InvalidIdTest() throws Exception {
        assertNull(dao.loginCheck("9999999", "1234"));
    }

    /**
     * 유효 ID, 틀린 PW로 로그인 실패 (null 반환)
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
     * 마이페이지 비밀번호 변경 성공 확인
     */
    @Test
    public void updatePasswordTest() throws Exception {
        String newPassword = UUID.randomUUID().toString(); // 새로운 임시 비밀번호
      
        assertEquals(1, dao.updatePassword("1004014", newPassword));
        assertEquals(1, dao.updatePassword("1004014", "8fT2kP1q!"));  // 테스트 직후 원래 비밀번호로 복구
    }
}