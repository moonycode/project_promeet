package com.oopsw.action;

import com.oopsw.action.detailAction.AddCheckListAction;
import com.oopsw.action.detailAction.AddCommentAction;
import com.oopsw.action.detailAction.AddReplyAction;
import com.oopsw.action.detailAction.CheckListAction;
import com.oopsw.action.detailAction.CommentReplyAction;
import com.oopsw.action.detailAction.DeleteCheckListAction;
import com.oopsw.action.detailAction.DeleteCommentAction;
import com.oopsw.action.detailAction.DeleteReplyAction;
import com.oopsw.action.detailAction.DetailTaskUIAction;
import com.oopsw.action.detailAction.UpdateCheckListAction;
import com.oopsw.action.detailAction.UpdateCommentAction;
import com.oopsw.action.detailAction.UpdateReplyAction;
import com.oopsw.action.employeeAction.LoginAction;
import com.oopsw.action.employeeAction.LoginUIAction;
import com.oopsw.action.projectAction.AddProjectAction;
import com.oopsw.action.projectAction.AddProjectUIAction;
import com.oopsw.action.projectAction.BinProjectAction;
import com.oopsw.action.projectAction.BinProjectUIAction;
import com.oopsw.action.projectAction.CompleteProjectAction;
import com.oopsw.action.projectAction.DeleteProjectAction;
import com.oopsw.action.projectAction.ProjectAction;
import com.oopsw.action.projectAction.ProjectUIAction;
import com.oopsw.action.projectAction.RestoreProjectAction;
import com.oopsw.action.projectAction.UpdateProjectAction;
import com.oopsw.action.projectAction.UpdateProjectUIAction;

public class ActionFactory {	
	private ActionFactory(){}

	public static Action getAction(String cmd){
		Action a=null;
		
		switch(cmd){
		case "getBinProject":
			a = new BinProjectAction();
			break;
		case "restoreProject":
			a = new RestoreProjectAction();
			break;
		case "binProjectUI":
			a = new BinProjectUIAction();
			break;
		case "getProject":
			a = new ProjectAction();
			break;
		case "deleteReply":
			a =new DeleteReplyAction();
			break;
		case "updateReply":
			a =new UpdateReplyAction();
			break;
		case "addReply":
			a =new AddReplyAction();
			break;
		case "deleteComment":
			a =new DeleteCommentAction();
			break;
		case "updateComment":
			a =new UpdateCommentAction();
			break;
		case "addComment":
			a =new AddCommentAction();
			break;
		case "commentReply":
			a =new CommentReplyAction();
			break;
		case "deleteCheckList":
			a =new DeleteCheckListAction();
			break;
		case "updateCheckList":
			a =new UpdateCheckListAction();
			break;
		case "addCheckList":
			a =new AddCheckListAction();
			break;
		case "checkList":
			a =new CheckListAction();
			break;
		case "detailTaskUI":
			a = new DetailTaskUIAction();
			break;
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
