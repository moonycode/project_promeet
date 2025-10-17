package test.com.oopsw.model;


import java.util.List;

import org.junit.Test;

import com.oopsw.model.FileBoxDAO;
import com.oopsw.model.ProjectVO;
import com.oopsw.model.SearchFileBoxVO;
import com.oopsw.model.ProjectFileBoxVO;

public class FileBoxDAOTest {


    @Test
    public void testGetProjects() {
    	FileBoxDAO dao = new FileBoxDAO();
        List<ProjectVO> projects = dao.getProjects();
        for (ProjectVO projectVO : projects) {
            System.out.println(projectVO.getProjectNo() + "/" +projectVO.getProjectName() + " / " + projectVO.getClient() + " / " + projectVO.getDescription());
        }
    }
    
    @Test
    public void testSearchFiles() {
        FileBoxDAO dao = new FileBoxDAO();
        String keyword = "JPA";

        List<SearchFileBoxVO> result = dao.searchFilesOrTask(keyword, null);
        System.out.println(result);
        }
    
    @Test
    public void testSearchTask() {
        FileBoxDAO dao = new FileBoxDAO();
        Integer taskNo = 15;

        List<SearchFileBoxVO> result = dao.searchFilesOrTask(null, taskNo);
        System.out.println(result);
        }
    
    @Test
    public void testProjectFileBox() {
    	List<ProjectFileBoxVO> result = new FileBoxDAO().getProjectNameTaskFileCount(8);
    	System.out.println(result);
    }
}