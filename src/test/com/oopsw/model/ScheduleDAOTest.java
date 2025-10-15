package test.com.oopsw.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import com.oopsw.model.ScheduleDAO;
import com.oopsw.model.ScheduleVO;

public class ScheduleDAOTest {

	//일정관리 전체 조회
	//일정관리 특정 날짜 조회
    @Test
    public void getDotScheduleTest() {
        ScheduleDAO dao = new ScheduleDAO();
        String employeeId = "1004014";
        List<ScheduleVO> list = dao.getDotSchedule(employeeId);
        assertNotNull("리스트가 null이면 안됨", list);
        System.out.println("조회된 일정 개수: " + list.size());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        
        if (!list.isEmpty()) {
            for (ScheduleVO vo : list) {
            	String dateStr = sdf.format(vo.getScheduleDate());
                System.out.println("날짜: " + dateStr);
                System.out.println("타입: " + vo.getScheduleType());
                System.out.println("제목: " + vo.getTitle());
                String startTimeStr = sdf2.format(vo.getStartTime());
                System.out.println("시작시간: " + startTimeStr);
                String endTimeStr = sdf2.format(vo.getEndTime());                
                System.out.println("종료시간: " + endTimeStr);
            }
        }
    }
    
	@Test
	public void addScheduleTest(){
		Date scheduleDate = Date.valueOf("2025-10-11");
		Timestamp startTime = Timestamp.valueOf("2025-10-11 09:00:00");
		Timestamp endTime = Timestamp.valueOf("2025-10-11 18:00:00");
		new ScheduleDAO().addSchedule(
//String employeeId, Date scheduleDate, String scheduleType, String title, Date startTime, Date endTime
				new ScheduleVO("1004014", scheduleDate, "green", "연차", startTime, endTime)
				);
	}
	
	@Test
	public void deleteScheduleTest() {
			assertTrue(new ScheduleDAO().deleteSchedule(17));

	}
	
	@Test
	public void updateScheduleTest() {
	    ScheduleVO vo = new ScheduleVO(
	        17,                    // scheduleNo
	        "1004014",          // employeeId
	        Date.valueOf("2025-10-12"), // scheduleDate
	        "blue",                // scheduleType
	        "테스트 수정",           // title
	        Timestamp.valueOf("2025-10-12 09:00:00"), // startTime
	        Timestamp.valueOf("2025-10-12 18:00:00")  // endTime
	    );

	    new ScheduleDAO().updateSchedule(vo);
	}

}
