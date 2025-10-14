package com.oopsw.action.detailAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;

public class AddCheckListAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String taskNo = request.getParameter("taskNo");
		String[] contents = request.getParameterValues("contents"); // n개의 contents가 넘어올 수 있음
		List<CheckListVO> list = new ArrayList<>();
		int result = 0;

		try{
			if(contents != null){
				for (String content : contents) {
					// 내용이 비어있지 않을 때만 추가
					if (content != null && !content.trim().isEmpty()) {
						list.add(new CheckListVO(Integer.valueOf(taskNo), content));
					}
				}
				result = new CheckListDAO().addChecklist(list);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "checkList.jsp";
	}

}
