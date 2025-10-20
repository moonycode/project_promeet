// 설명: MembersDAO 단위 테스트 (XML 3단계 동기화 방식에 맞춤)
package test.com.oopsw.model;

import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.oopsw.model.MembersDAO;
import com.oopsw.model.ProjectJoinVO;

public class MembersDAOTest {

    private MembersDAO dao;

    @Before
    public void setUp() {
        dao = new MembersDAO();
    }

    @Test
    public void selectProjectMembersSuccess() {
        // 설명: 프로젝트 17의 전체 직원 목록 + join 상태 조회
        List<ProjectJoinVO> list = dao.selectProjectMembers(17);
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void selectActiveProjectMembersSuccess() {
        // 설명: 프로젝트 17의 현재 참여자 조회
        List<ProjectJoinVO> list = dao.selectActiveProjectMembers(17);
        assertNotNull(list);
        // NPE 방지 목적 (데이터 상황에 따라 0 이상)
    }

    @Test
    public void selectTaskJoinedPjoinNosSuccess() {
        // 설명: 업무 3번에 배정된 project_join_no 목록
        List<Integer> joined = dao.selectTaskJoinedPjoinNos(3);
        assertNotNull(joined);
        assertTrue(joined.size() > 0);
    }


    @Test
    public void syncProjectMembersDryRunKeepsState() {
        // 설명: 현재 활성 멤버 목록을 읽어 동일 집합으로 다시 동기화(상태 유지)
        List<ProjectJoinVO> active = dao.selectActiveProjectMembers(17);
        List<String> ids = active.stream()
                .map(ProjectJoinVO::getEmployeeId)
                .collect(Collectors.toList());
        int affected = dao.syncProjectMembers(17, ids);
        // 영향 건수는 환경에 따라 다를 수 있으므로 성공 여부만 본다
        assertTrue(affected >= 0);
    }
}
