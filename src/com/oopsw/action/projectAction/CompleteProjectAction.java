package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;

// cmd=completeProject�� ó���Ͽ� ������Ʈ�� �Ϸ� ���·� �����մϴ�.
public class CompleteProjectAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		// �Ϸ� ����/���п� ������� ������Ʈ ������� �����̷�Ʈ
		String url = "controller?cmd=projectUI"; 
		
		// 1. ��û �Ķ����(projectNo) �ޱ�
		String projectNoStr = request.getParameter("projectNo");
		
		if (projectNoStr == null || projectNoStr.isEmpty()) {
			request.setAttribute("msg", "������ ������Ʈ ��ȣ�� �����Ǿ����ϴ�.");
			return url;
		}
		
		try {
			int projectNo = Integer.parseInt(projectNoStr);
			
			// 2. DB�� ������Ʈ �Ϸ� ó��
			ProjectDAO projectDAO = new ProjectDAO();
			
            // DAO �޼ҵ� ȣ�� (completeProject �޼ҵ尡 �ִٰ� ����)
            int result = projectDAO.completeProject(projectNo); 
			
			if (result > 0) {
				// �Ϸ� ���� �� �޽��� (�ʿ��ϴٸ� ���ǿ� �����Ͽ� projectUI���� ���)
				// request.getSession().setAttribute("msg", "������Ʈ�� ���������� �Ϸ� ó���Ǿ����ϴ�.");
			} else {
				// �Ϸ� ���� �� �޽��� (projectUI���� ����ϵ��� ����)
				request.setAttribute("msg", "������Ʈ �Ϸ� ó�� �� DB ������ �߻��߽��ϴ�.");
			}

		} catch (NumberFormatException e) {
			request.setAttribute("msg", "�߸��� ������Ʈ ��ȣ �����Դϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "������Ʈ �Ϸ� ó�� �� �ý��� ���� �߻�.");
		}

		return url; // projectUI�� ���� �̵�
	}
}