package com.oopsw.model;

public class FileBoxVO {
    private int taskNo;
    private String taskName;
    private int totalFileCount;

    private int commentNo;
    private String commentFile;
    private String commentPath;
    private int replyNo;
    private String replyFile;
    private String replyPath;
    
    
	public FileBoxVO() {}
	public FileBoxVO(int taskNo, String taskName, int totalFileCount, int commentNo, String commentFile,
			String commentPath, int replyNo, String replyFile, String replyPath) {
		super();
		this.taskNo = taskNo;
		this.taskName = taskName;
		this.totalFileCount = totalFileCount;
		this.commentNo = commentNo;
		this.commentFile = commentFile;
		this.commentPath = commentPath;
		this.replyNo = replyNo;
		this.replyFile = replyFile;
		this.replyPath = replyPath;
	}
	
	public int getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getTotalFileCount() {
		return totalFileCount;
	}
	public void setTotalFileCount(int totalFileCount) {
		this.totalFileCount = totalFileCount;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getCommentFile() {
		return commentFile;
	}
	public void setCommentFile(String commentFile) {
		this.commentFile = commentFile;
	}
	public String getCommentPath() {
		return commentPath;
	}
	public void setCommentPath(String commentPath) {
		this.commentPath = commentPath;
	}
	public int getReplyNo() {
		return replyNo;
	}
	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}
	public String getReplyFile() {
		return replyFile;
	}
	public void setReplyFile(String replyFile) {
		this.replyFile = replyFile;
	}
	public String getReplyPath() {
		return replyPath;
	}
	public void setReplyPath(String replyPath) {
		this.replyPath = replyPath;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentFile == null) ? 0 : commentFile.hashCode());
		result = prime * result + commentNo;
		result = prime * result + ((commentPath == null) ? 0 : commentPath.hashCode());
		result = prime * result + ((replyFile == null) ? 0 : replyFile.hashCode());
		result = prime * result + replyNo;
		result = prime * result + ((replyPath == null) ? 0 : replyPath.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + taskNo;
		result = prime * result + totalFileCount;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileBoxVO other = (FileBoxVO) obj;
		if (commentFile == null) {
			if (other.commentFile != null)
				return false;
		} else if (!commentFile.equals(other.commentFile))
			return false;
		if (commentNo != other.commentNo)
			return false;
		if (commentPath == null) {
			if (other.commentPath != null)
				return false;
		} else if (!commentPath.equals(other.commentPath))
			return false;
		if (replyFile == null) {
			if (other.replyFile != null)
				return false;
		} else if (!replyFile.equals(other.replyFile))
			return false;
		if (replyNo != other.replyNo)
			return false;
		if (replyPath == null) {
			if (other.replyPath != null)
				return false;
		} else if (!replyPath.equals(other.replyPath))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (taskNo != other.taskNo)
			return false;
		if (totalFileCount != other.totalFileCount)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TaskFileCountVO [taskNo=" + taskNo + ", taskName=" + taskName + ", totalFileCount=" + totalFileCount
				+ ", commentNo=" + commentNo + ", commentFile=" + commentFile + ", commentPath=" + commentPath
				+ ", replyNo=" + replyNo + ", replyFile=" + replyFile + ", replyPath=" + replyPath + "]";
	}
    
    
}
