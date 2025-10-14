package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;

public class DeleteProjectAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		// ���� ����/���п� ������� ������Ʈ ������� �����̷�Ʈ
		String url = "controller?cmd=projectUI"; 
		
		// 1. ��û �Ķ����(projectNo) �ޱ�
		String projectNoStr = request.getParameter("projectNo");
		
		if (projectNoStr == null || projectNoStr.isEmpty()) {
			request.setAttribute("msg", "������ ������Ʈ ��ȣ�� �����Ǿ����ϴ�.");
			return url;
		}
		
		try {
			int projectNo = Integer.parseInt(projectNoStr);
			
			// 2. DB�� ������Ʈ ���� ó��
			ProjectDAO projectDAO = new ProjectDAO();

            int result = projectDAO.deleteProject(projectNo); 
			if (result > 0) {
				// ����: projectUI�� �̵��Ͽ� ���ŵ� ��� ǥ��
			} else {
				// ����: ���� �޽���
				request.setAttribute("msg", "������Ʈ ���� ó�� �� DB ������ �߻��߰ų�, �̹� ������ ������Ʈ�Դϴ�.");
			}

		} catch (NumberFormatException e) {
			request.setAttribute("msg", "�߸��� ������Ʈ ��ȣ �����Դϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "������Ʈ ���� ó�� �� �ý��� ���� �߻�.");
		}

		return url;
	}
}