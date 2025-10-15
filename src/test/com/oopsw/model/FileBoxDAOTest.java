package test.com.oopsw.model;

import java.util.List;

import org.junit.Test;

import com.oopsw.model.FileBoxDAO;
import com.oopsw.model.FileBoxVO;
import com.oopsw.model.ProjectVO;

public class FileBoxDAOTest {

    private FileBoxDAO fileBoxDAO = new FileBoxDAO();

    @Test
    public void testGetProjects() {
        List<ProjectVO> projects = fileBoxDAO.getProjects();
        for (ProjectVO projectVO : projects) {
            System.out.println(projectVO.getProjectNo() + "/" +projectVO.getProjectName() + " / " + projectVO.getClient() + " / " + projectVO.getDescription());
        }
    }

    @Test
    public void testGetProjectNameTaskFileCount() {
        int projectNo = 18;
        List<FileBoxVO> list = fileBoxDAO.getProjectNameTaskFileCount(projectNo);
        for (FileBoxVO fileBoxVO : list) {
            System.out.println(fileBoxVO.getTaskName() + " : " + fileBoxVO.getTotalFileCount() + "°³");
        }
    }

    @Test
    public void testSearchFiles() {
        String keyword = "JPA";
        List<FileBoxVO> files = fileBoxDAO.searchFiles(keyword);
        for (FileBoxVO f : files) {
            System.out.println(f.getCommentFile() + " / " + f.getReplyFile());
        }
    }

    @Test
    public void testGetTaskFiles() {
        int taskNo = 15;
        List<FileBoxVO> files = fileBoxDAO.getTaskFiles(taskNo);
        for (FileBoxVO f : files) {
            System.out.println("Comment: " + f.getCommentFile() + ", Reply: " + f.getReplyFile());
        }
    }
}