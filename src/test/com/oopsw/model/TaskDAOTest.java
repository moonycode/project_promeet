// 설명: TaskDAO 단위 테스트 (mapper XML과 정합성 확보, 리터럴 사용)
package test.com.oopsw.model;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.Before;
import org.junit.Test;

import com.oopsw.model.TaskDAO;
import com.oopsw.model.TaskVO;

public class TaskDAOTest {

    private TaskDAO dao;

    @Before
    public void setUp() {
        dao = new TaskDAO();
    }

    @Test
    public void selectTasksByProjectSuccess() {
        // 설명: 프로젝트 17번의 업무 목록 조회 (샘플데이터 기준 존재)
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", 17);
        p.put("taskStatus", "ALL");
        p.put("priority", "ALL");
        p.put("orderBy", "");
        List<TaskVO> list = dao.selectTasksByProject(p);
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void insertTaskSuccess() {
        // 설명: mapper가 #{taskStatus}를 기대하므로 기본값 '대기' 포함해 insert
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", 17);
        p.put("taskName", "T-" + System.currentTimeMillis());
        p.put("taskStatus", "대기");
        p.put("priority", "보통");
        p.put("startDate", "");
        p.put("endDate", "");
        assertTrue(dao.insertTask(p));
    }

    @Test
    public void updateTaskSuccess() {
        // 설명: 새 업무 추가 → 바로 일부 필드 수정
        Map<String,Object> add = new HashMap<>();
        add.put("projectNo", 17);
        add.put("taskName", "U-" + System.currentTimeMillis());
        add.put("taskStatus", "대기");
        add.put("priority", "보통");
        add.put("startDate", "");
        add.put("endDate", "");
        assertTrue(dao.insertTask(add));

        Map<String,Object> q = new HashMap<>();
        q.put("projectNo", 17);
        q.put("taskStatus", "ALL");
        q.put("priority", "ALL");
        q.put("orderBy", "");
        List<TaskVO> list = dao.selectTasksByProject(q);
        assertNotNull(list);
        assertTrue(list.size() > 0);

        TaskVO t = list.get(list.size() - 1); // 방금 추가된 것으로 가정
        Map<String,Object> upd = new HashMap<>();
        upd.put("taskNo", t.getTaskNo());
        upd.put("taskName", t.getTaskName() + "-수정");
        upd.put("taskStatus", t.getTaskStatus());
        upd.put("priority", t.getPriority());
        upd.put("startDate", "");
        upd.put("endDate", "");
        assertTrue(dao.updateTaskBasicMap(upd));
    }

    @Test
    public void updateTaskFailOnNotExist() {
        // 설명: 존재하지 않는 업무번호로 수정 시도 → 실패 기대
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", 999999);
        p.put("taskName", "없는업무");
        p.put("taskStatus", "진행");
        p.put("priority", "보통");
        p.put("startDate", "");
        p.put("endDate", "");
        assertFalse(dao.updateTaskBasicMap(p));
    }

    @Test
    public void deleteSuccess() {
        // 설명: 새 업무 추가 후 즉시 삭제(soft)
        Map<String,Object> add = new HashMap<>();
        add.put("projectNo", 17);
        add.put("taskName", "D-" + System.currentTimeMillis());
        add.put("taskStatus", "대기");
        add.put("priority", "보통");
        add.put("startDate", "");
        add.put("endDate", "");
        assertTrue(dao.insertTask(add));

        Map<String,Object> q = new HashMap<>();
        q.put("projectNo", 17);
        q.put("taskStatus", "ALL");
        q.put("priority", "ALL");
        q.put("orderBy", "");
        List<TaskVO> list = dao.selectTasksByProject(q);
        assertNotNull(list);
        assertTrue(list.size() > 0);

        TaskVO t = list.get(list.size() - 1);
        assertTrue(dao.deleteTask(t.getTaskNo()));
    }

    @Test
    public void deleteFailOnNotExist() {
        // 설명: 존재하지 않는 업무번호 삭제 시도 → 실패 기대
        assertFalse(dao.deleteTask(999999));
    }
}
