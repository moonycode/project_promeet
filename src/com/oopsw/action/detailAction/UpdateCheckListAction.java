package com.oopsw.action.detailAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;

public class UpdateCheckListAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String[] checkListNo = request.getParameterValues("checkListNo");
		String[] contents = request.getParameterValues("contents");
		String[] completeFlag = request.getParameterValues("completeFlag");
		List<CheckListVO> list = new ArrayList<>();
		int result = 0;
		try{
			if (checkListNo != null && contents != null && completeFlag != null) {
				for (int i = 0; i < checkListNo.length; i++) {
					list.add(new CheckListVO(Integer.valueOf(checkListNo[i]), contents[i], Integer.valueOf(completeFlag[i])));
				}

				result = new CheckListDAO().updateChecklist(list);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	

		request.setAttribute("result",result);
		return "checkList.jsp";

	}
}