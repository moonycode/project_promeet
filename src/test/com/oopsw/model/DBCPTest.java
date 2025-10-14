package test.com.oopsw.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oopsw.model.DBCP;

public class DBCPTest {

	@Test
	public void test() {
		System.out.println(DBCP.getSqlSessionFactory());
		System.out.println(DBCP.getSqlSessionFactory().openSession());
		System.out.println(DBCP.getSqlSessionFactory().openSession().getConnection());
	}
	


}
