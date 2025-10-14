package com.oopsw.model;

import java.util.Date;

public class CommentVO {
	private int commentNo;
	private int taskNo;
	private int projectJoinNo;
	private String contents;
	private Date inDate;
	private Date upDate;
	private String fileName;
	private String filePath;
	private Date delDate;
	
	
	public CommentVO(){}
	public CommentVO(int commentNo,String contents) {
		setCommentNo(commentNo);
		setContents(contents);
	}
	public CommentVO(int taskNo, int projectJoinNo, String contents,String fileName, String filePath) {
		setTaskNo(taskNo);
		setProjectJoinNo(projectJoinNo);
		setContents(contents);
		setFileName(fileName);
		setFilePath(filePath);
	}
	public CommentVO(int commentNo, int taskNo, int projectJoinNo, String contents, Date inDate, Date upDate,
			String fileName, String filePath, Date delDate) {
		setCommentNo(commentNo);
		setTaskNo(taskNo);
		setProjectJoinNo(projectJoinNo);
		setContents(contents);
		setInDate(inDate);
		setUpDate(upDate);
		setFileName(fileName);
		setFilePath(filePath);
		setDelDate(delDate);
	}

	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	@Override
	public String toString() {
		return "CommentVO [commentNo=" + commentNo + ", taskNo=" + taskNo + ", projectJoinNo=" + projectJoinNo
				+ ", contents=" + contents + ", inDate=" + inDate + ", upDate=" + upDate + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", delDate=" + delDate + "]";
	}
	
	
	
}
