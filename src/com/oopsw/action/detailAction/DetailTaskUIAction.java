package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;

public class DetailTaskUIAction implements Action{
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		//Ȥ�� ó���� �ִٸ� �����ڵ尡 ���� �Ǵµ� �ִ� �ܼ��� ���Ḹ �ϴ°Ŷ� �����ڵ尡 �ʿ����.
		return "details.jsp";
	}
	
}
