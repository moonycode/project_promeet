package com.oopsw.action;

import com.oopsw.action.employeeAction.LoginAction;
import com.oopsw.action.employeeAction.LoginUIAction;
import com.oopsw.action.projectAction.AddProjectAction;
import com.oopsw.action.projectAction.AddProjectUIAction;
import com.oopsw.action.projectAction.CompleteProjectAction;
import com.oopsw.action.projectAction.DeleteProjectAction;
import com.oopsw.action.projectAction.ProjectUIAction;
import com.oopsw.action.projectAction.UpdateProjectAction;
import com.oopsw.action.projectAction.UpdateProjectUIAction;

public class ActionFactory {	
	private ActionFactory(){}

	public static Action getAction(String cmd){
		Action a=null;
		
		switch(cmd){
		case "deleteProject":
			a = new DeleteProjectAction();
			break;
		case "completeProject":
			a = new CompleteProjectAction();
			break;
		case "updateProject":
			a = new UpdateProjectAction();
			break;
		case "updateProjectUI":
			a = new UpdateProjectUIAction();
			break;
		case "addProjectUI":
			a = new AddProjectUIAction();
			break;
		case "addProject":
			a = new AddProjectAction();
			break;
		case "projectUI":
			a = new ProjectUIAction();
			break;
		case "login":
			a = new LoginAction();
			break;
		case "mainUI":
		case "loginUI":
			a = new LoginUIAction();
			break;
		default:
			a = new LoginUIAction();
		}
		return a;
	}
	
}
