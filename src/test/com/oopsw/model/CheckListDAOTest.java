package test.com.oopsw.model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;

import junit.framework.Assert;

public class CheckListDAOTest {
	static Connection conn;
	static CheckListDAO dao;

	@Before
	public void start() {
		dao = new CheckListDAO();
	}
	@Test
	public void getChecklistTest(){
		System.out.println(dao.getChecklist(3));
	}
	@Test
	public void addChecklistTest(){
		List<CheckListVO> list = new ArrayList<>();
        list.add(new CheckListVO(3, "addtest1"));
        list.add(new CheckListVO(3, "addtest2"));
        assertEquals(dao.addChecklist(list),2);
	}
	@Test
	public void updateChecklistTest(){
		List<CheckListVO> list = new ArrayList<>();
        list.add(new CheckListVO(18, "수정test1",1));
        list.add(new CheckListVO(1, "수정test2",0));
		assertEquals(dao.updateChecklist(list), 2);
	}

	@Test
	public void deleteChecklistTest(){
		assertEquals(dao.deleteChecklist(18), 1);
	}

}
