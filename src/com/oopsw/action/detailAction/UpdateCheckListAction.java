package com.oopsw.action.detailAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.CheckListDAO;
import com.oopsw.model.CheckListVO;

import util.CreateJsonResponse;
import util.JsonResponse;

public class UpdateCheckListAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String[] checkListNo = request.getParameterValues("checkListNo");
		String[] contents = request.getParameterValues("contents");
		String[] completeFlag = request.getParameterValues("completeFlag");
		List<CheckListVO> list = new ArrayList<>();
		try{
			if (checkListNo != null && contents != null && completeFlag != null) {
				for (int i = 0; i < checkListNo.length; i++) {
					list.add(new CheckListVO(Integer.valueOf(checkListNo[i]), contents[i], Integer.valueOf(completeFlag[i])));
				}
				JsonResponse<Integer> response = 
						new JsonResponse<>("success", "수정 완료",new CheckListDAO().updateChecklist(list));
				String jsonResponse = CreateJsonResponse.toJson(response);
				request.setAttribute("jsonResponse", jsonResponse);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	

		return "Json/jsonResult.jsp";

	}
}