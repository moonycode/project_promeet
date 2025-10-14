package com.oopsw.model;

import java.util.Date;

public class TaskJoinVO {

	private int taskJoinNo;
	private int taskNo;
	private int projectJoinNo;
	private Date inDate;
	private Date upDate;
	private int joinFlag;


	public TaskJoinVO(){}
	public TaskJoinVO(int taskJoinNo, int taskNo, int projectJoinNo, Date inDate, Date upDate, int joinFlag) {
		setTaskJoinNo(taskJoinNo);
		setTaskNo(taskNo);
		setProjectJoinNo(projectJoinNo);
		setInDate(inDate);
		setUpDate(upDate);
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
	public Date getUpDate() {
		return upDate;
	}
	public void setUpDate(Date upDate) {
		this.upDate = upDate;
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
				+ ", inDate=" + inDate + ", upDate=" + upDate + ", joinFlag=" + joinFlag + "]";
	}



}
