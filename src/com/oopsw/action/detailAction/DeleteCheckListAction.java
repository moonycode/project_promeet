package com.oopsw.action.detailAction;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;

public class DeleteCheckListAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String checkListNo = request.getParameter("checkListNo");
		int result = 0;
		try{
			result = new CheckListDAO().deleteChecklist(Integer.valueOf(checkListNo));
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "checkList.jsp";
	}

}
