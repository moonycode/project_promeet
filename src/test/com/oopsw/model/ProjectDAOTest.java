package test.com.oopsw.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

public class ProjectDAOTest {
	static ProjectDAO dao;

    @Before
	public void start() {
		dao = new ProjectDAO();
	}

    @Test
    public void getOngoingProjectsTest() throws Exception {
        assertNotNull(dao.getOngoingProjects("1004014"));
    }

    @Test
    public void getCompletedProjectsTest() throws Exception {
        assertNotNull(dao.getCompletedProjects("1004014"));
    }

    @Test
    public void selectUpdateProjectTest() throws Exception {
        assertNotNull(dao.getProjectByNo(13));
    }

    @Test
    public void getBinProjectsTest() throws Exception {
        assertNotNull(dao.getBinProjects("1004014"));
    }
    
    @Test
    public void insertProjectTest() throws Exception {
        ProjectVO newProject = new ProjectVO();

        newProject.setCreatorId("1004014");
        newProject.setProjectName("JUnit Simple Insert: " + System.currentTimeMillis());
        newProject.setClient("Simple Client");
        newProject.setDescription("���� ���� DAO �׽�Ʈ�� ������Ʈ ���.");
        
        // ������Ʈ�� �Ŵ��� ����� ��� �����ϸ� 1 
        assertEquals(1, dao.insertProject(newProject, "2026-01-01", "2026-12-31"));
    }

    @Test
    public void updateProjectTest() throws Exception {
        ProjectVO vo = dao.getProjectByNo(13);
        vo.setProjectNo(13);
        vo.setProjectName("JUnit Simple Update: " + System.currentTimeMillis()); // �̸� ����

        assertEquals(1, dao.updateProject(vo, "2026-03-01", "2026-06-30"));
    }

    @Test
    public void completeProjectTest() throws Exception {
        assertEquals(1, dao.completeProject(17)); 
    }

    @Test
    public void deleteProjectTest() throws Exception {
        assertEquals(1, dao.deleteProject(18)); 
    }

    @Test
    public void restoreProjectTest() throws Exception {
        assertEquals(1, dao.restoreProject(14)); 
    }
}