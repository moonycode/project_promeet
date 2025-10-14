package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

public class UpdateProjectUIAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		String projectNoStr = request.getParameter("projectNo");
		String url = "updateProject.jsp"; // ���� JSP ����
		
		if (projectNoStr == null || projectNoStr.isEmpty()) {
			// projectNo�� ������ ������� ���������ų� ���� �޽����� ǥ��
			request.setAttribute("msg", "������ ������Ʈ�� �������ּ���.");
			return "controller?cmd=projectUI";
		}
		
		try {
			int projectNo = Integer.parseInt(projectNoStr);
			// 2. DB���� �ش� projectNo�� ������ ��ȸ (DAO �޼ҵ� �ʿ�)
			ProjectDAO projectDAO = new ProjectDAO();
			// DAO �޼ҵ� ȣ�� (getProjectByNo �޼ҵ尡 �ִٰ� ����)
			ProjectVO projectVO = projectDAO.selectUpdateProject(projectNo);
			
			if (projectVO != null) {
				// 3. ��ȸ�� �����͸� request�� �����Ͽ� JSP�� ������
				request.setAttribute("project", projectVO);
			} else {
				// ��ȸ ����� ������ ���� �޽���
				request.setAttribute("msg", "�ش� ������Ʈ�� ã�� �� �����ϴ�.");
				url = "controller?cmd=projectUI";
			}
			
		} catch (NumberFormatException e) {
			request.setAttribute("msg", "�߸��� ������Ʈ ��ȣ �����Դϴ�.");
			url = "controller?cmd=projectUI";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "������Ʈ ��ȸ �� �ý��� ���� �߻�.");
			url = "controller?cmd=projectUI";
		}

		return url;
	}
}