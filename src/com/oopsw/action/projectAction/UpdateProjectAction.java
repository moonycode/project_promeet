package com.oopsw.action.projectAction;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ProjectDAO;
import com.oopsw.model.ProjectVO;

// cmd=updateProject�� ó���Ͽ� DB�� ������Ʈ ������ ������Ʈ�մϴ�.
public class UpdateProjectAction implements Action {

	@Override
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		// ���� ���� ��, �ش� ������Ʈ�� Task �������� �����̷�Ʈ
		String projectNo = request.getParameter("projectNo");
		String url = "controller?cmd=taskUI&projectNo=" + projectNo; 
		
		try {
			// 1. ��û �Ķ���� �ޱ� �� VO�� ����
			ProjectVO projectVO = new ProjectVO();
            
            // �ʼ�: projectNo�� int�� ��ȯ
            int pNo = Integer.parseInt(projectNo);
            projectVO.setProjectNo(pNo); 
            
			projectVO.setProjectName(request.getParameter("projectName"));
			projectVO.setClient(request.getParameter("client"));
			projectVO.setDescription(request.getParameter("description"));
			
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

			// 2. DB�� ������Ʈ ���� ������Ʈ ó��
			ProjectDAO projectDAO = new ProjectDAO();
			
            int result = projectDAO.updateProject(projectVO, startDate, endDate); 
			
			if (result > 0) {
				// ����: Task �������� �����̷�Ʈ (TaskUI�� ������ projectUI�� ����)
			} else {
				// ����: ���� ������ ���ư� ���� �޽��� ���
				request.setAttribute("msg", "������Ʈ ������ �����߽��ϴ�. DB �Է� ����.");
                // ���� ������ �����͸� �ٽ� �����ֱ� ���� projectVO�� request�� �ٽ� ����
                request.setAttribute("project", projectVO);
				url = "updateProject.jsp"; 
			}

		} catch (NumberFormatException e) {
			request.setAttribute("msg", "�߸��� ������Ʈ ��ȣ �����Դϴ�.");
			url = "controller?cmd=projectUI";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "������Ʈ ���� �� �ý��� ���� �߻�.");
			url = "updateProject.jsp"; 
		}

		return url; 
	}
}