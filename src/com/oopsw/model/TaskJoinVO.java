package com.oopsw.model;

import java.util.Date;

public class TaskJoinVO {

	private int taskJoinNo;
	private int taskNo;
	private int projectJoinNo;
	private Date inDate;
	private Date dateUp;
	private int joinFlag;


	public TaskJoinVO(){}
	public TaskJoinVO(int taskJoinNo, int taskNo, int projectJoinNo, Date inDate, Date upDate, int joinFlag) {
		setTaskJoinNo(taskJoinNo);
		setTaskNo(taskNo);
		setProjectJoinNo(projectJoinNo);
		setInDate(inDate);
		setDateUp(dateUp);
		setJoinFlag(joinFlag);
	}
	public int getTaskJoinNo() {
		return taskJoinNo;
	}
	public void setTaskJoinNo(int taskJoinNo) {
		this.taskJoinNo = taskJoinNo;
	}
	public int getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}
	public int getProjectJoinNo() {
		return projectJoinNo;
	}
	public void setProjectJoinNo(int projectJoinNo) {
		this.projectJoinNo = projectJoinNo;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Date getDateUp() {
		return dateUp;
	}
	public void setDateUp(Date dateUp) {
		this.dateUp = dateUp;
	}
	public int getJoinFlag() {
		return joinFlag;
	}
	public void setJoinFlag(int joinFlag) {
		this.joinFlag = joinFlag;
	}
	@Override
	public String toString() {
		return "TaskJoinVO [taskJoinNo=" + taskJoinNo + ", taskNo=" + taskNo + ", projectJoinNo=" + projectJoinNo
				+ ", inDate=" + inDate + ", dateUp=" + dateUp + ", joinFlag=" + joinFlag + "]";
	}



}
