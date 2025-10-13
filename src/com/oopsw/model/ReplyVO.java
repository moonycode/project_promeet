package com.oopsw.model;

import java.util.Date;

public class ReplyVO {
	private int replyNo;
	private int projectJoinNo;
	private int commentNo;
	private String contents;
	private Date inDate;
	private Date upDate;
	private String fileName;
	private String filePath;
	private Date uploadDate;
	private Date delDate;
	
	
	public ReplyVO() {}
	public ReplyVO(int replyNo,String contents) {
		setReplyNo(replyNo);
		setContents(contents);
	}
	public ReplyVO(int commentNo, int projectJoinNo, String contents,String fileName, String filePath) {
		setCommentNo(commentNo);
		setProjectJoinNo(projectJoinNo);
		setContents(contents);
		setFileName(fileName);
		setFilePath(filePath);
	}
	public ReplyVO(int replyNo, int projectJoinNo, int commentNo, String contents, Date inDate, Date upDate,
			String fileName, String filePath, Date uploadDate, Date delDate) {
		setReplyNo(replyNo);
		setProjectJoinNo(projectJoinNo);
		setCommentNo(commentNo);
		setContents(contents);
		setInDate(inDate);
		setUpDate(upDate);
		setFileName(fileName);
		setFilePath(filePath);
		setUploadDate(uploadDate);
		setDelDate(delDate);
	}
	
	



	public int getReplyNo() {
		return replyNo;
	}
	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}
	public int getProjectJoinNo() {
		return projectJoinNo;
	}
	public void setProjectJoinNo(int projectJoinNo) {
		this.projectJoinNo = projectJoinNo;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
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
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	@Override
	public String toString() {
		return "ReplyVO [replyNo=" + replyNo + ", projectJoinNo=" + projectJoinNo + ", commentNo=" + commentNo
				+ ", contents=" + contents + ", inDate=" + inDate + ", upDate=" + upDate + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", uploadDate=" + uploadDate + ", delDate=" + delDate + "]";
	}

	
	

	

	
	
}
