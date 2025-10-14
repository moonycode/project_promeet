package com.oopsw.model;

import java.util.Date;

public class CheckListVO {
	
	private int checkListNo;
	private int taskNo;
	private int completeFlag;
	private String contents;
	private Date inDate;
	private Date upDate;
	private Date delDate;
	
	
	
	public CheckListVO(){}
	public CheckListVO(int checkListNo, String contents, int completeFlag) {
		setCheckListNo(checkListNo);
		setContents(contents);
		setCompleteFlag(completeFlag);
	}
	public CheckListVO(int taskNo, String contents) {
		setTaskNo(taskNo);
		setContents(contents);
	}
	public CheckListVO(int checkListNo, int taskNo, int completeFlag, String contents, Date inDate, Date upDate,
			Date delDate) {
		setCheckListNo(checkListNo);
		setTaskNo(taskNo);
		setCompleteFlag(completeFlag);
		setContents(contents);
		setInDate(inDate);
		setUpDate(upDate);
		setDelDate(delDate);

	}
	public int getCheckListNo() {
		return checkListNo;
	}
	public void setCheckListNo(int checkListNo) {
		this.checkListNo = checkListNo;
	}
	public int getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}
	public int getCompleteFlag() {
		return completeFlag;
	}
	public void setCompleteFlag(int completeFlag) {
		this.completeFlag = completeFlag;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	@Override
	public String toString() {
		return "CheckListVO [checkListNo=" + checkListNo + ", taskNo=" + taskNo + ", completeFlag=" + completeFlag
				+ ", contents=" + contents + ", inDate=" + inDate + ", upDate=" + upDate + ", delDate=" + delDate + "]";
	}
	
	
	
}
