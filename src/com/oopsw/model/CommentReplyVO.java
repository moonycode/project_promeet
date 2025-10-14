package com.oopsw.model;

import java.util.Date;

public class CommentReplyVO {
    private int commentNo;
    private int replyNo;
	
    private String taskName;
    private String commentWriter;
    private String commentContents;
    private Date commentIndate;
    private String commentFilename;
    private String replyWriter;
    private String repliesContents;
    private Date repliesIndate;
    private String repliesFilename;
    private Date commentUpdate;
    private Date repliesUpdate;
    private Date commentDeldate;
    private Date repliesDeldate;
    
    public CommentReplyVO(){}
	public CommentReplyVO(String taskName, String commentWriter, String commentContents, Date commentIndate,
			String commentFilename, String replyWriter, String repliesContents, Date repliesIndate,
			String repliesFilename, Date commentUpdate, Date repliesUpdate, Date commentDeldate, Date repliesDeldate,int commentNo,int replyNo) {
		setTaskName(taskName);
		setCommentWriter(commentWriter);
		setCommentContents(commentContents);
		setCommentIndate(commentIndate);
		setCommentFilename(commentFilename);
		setReplyWriter(replyWriter);
		setRepliesContents(repliesContents);
		setRepliesIndate(repliesIndate);
		setRepliesFilename(repliesFilename);
		setCommentUpdate(commentUpdate);
		setRepliesUpdate(repliesUpdate);
		setCommentDeldate(commentDeldate);
		setRepliesDeldate(repliesDeldate);
		setCommentNo(commentNo);
		setReplyNo(replyNo);
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getCommentWriter() {
		return commentWriter;
	}
	public void setCommentWriter(String commentWriter) {
		this.commentWriter = commentWriter;
	}
	public String getCommentContents() {
		return commentContents;
	}
	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}
	public Date getCommentIndate() {
		return commentIndate;
	}
	public void setCommentIndate(Date commentIndate) {
		this.commentIndate = commentIndate;
	}
	public String getCommentFilename() {
		return commentFilename;
	}
	public void setCommentFilename(String commentFilename) {
		this.commentFilename = commentFilename;
	}
	public String getReplyWriter() {
		return replyWriter;
	}
	public void setReplyWriter(String replyWriter) {
		this.replyWriter = replyWriter;
	}
	public String getRepliesContents() {
		return repliesContents;
	}
	public void setRepliesContents(String repliesContents) {
		this.repliesContents = repliesContents;
	}
	public Date getRepliesIndate() {
		return repliesIndate;
	}
	public void setRepliesIndate(Date repliesIndate) {
		this.repliesIndate = repliesIndate;
	}
	public String getRepliesFilename() {
		return repliesFilename;
	}
	public void setRepliesFilename(String repliesFilename) {
		this.repliesFilename = repliesFilename;
	}
	public Date getCommentUpdate() {
		return commentUpdate;
	}
	public void setCommentUpdate(Date commentUpdate) {
		this.commentUpdate = commentUpdate;
	}
	public Date getRepliesUpdate() {
		return repliesUpdate;
	}
	public void setRepliesUpdate(Date repliesUpdate) {
		this.repliesUpdate = repliesUpdate;
	}
	public Date getCommentDeldate() {
		return commentDeldate;
	}
	public void setCommentDeldate(Date commentDeldate) {
		this.commentDeldate = commentDeldate;
	}
	public Date getRepliesDeldate() {
		return repliesDeldate;
	}
	public void setRepliesDeldate(Date repliesDeldate) {
		this.repliesDeldate = repliesDeldate;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public int getReplyNo() {
		return replyNo;
	}
	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}
	@Override
	public String toString() {
		return "CommentReplyVO [commentNo=" + commentNo + ", replyNo=" + replyNo + ", taskName=" + taskName
				+ ", commentWriter=" + commentWriter + ", commentContents=" + commentContents + ", commentIndate="
				+ commentIndate + ", commentFilename=" + commentFilename + ", replyWriter=" + replyWriter
				+ ", repliesContents=" + repliesContents + ", repliesIndate=" + repliesIndate + ", repliesFilename="
				+ repliesFilename + ", commentUpdate=" + commentUpdate + ", repliesUpdate=" + repliesUpdate
				+ ", commentDeldate=" + commentDeldate + ", repliesDeldate=" + repliesDeldate + "]";
	}
	
    
    
    
}
