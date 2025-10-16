package com.oopsw.model;

public class ProjectFileBoxVO {
	int taskNo;
	String taskName;
	int totalFileCount;
	
	
	
	public ProjectFileBoxVO() {
	}
	public ProjectFileBoxVO(int taskNo, String taskName, int totalFileCount) {
		super();
		this.taskNo = taskNo;
		this.taskName = taskName;
		this.totalFileCount = totalFileCount;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ProjectFileBoxVO other = (ProjectFileBoxVO) obj;
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
		return "projectFileBoxVO [taskNo=" + taskNo + ", taskName=" + taskName + ", totalFileCount=" + totalFileCount
				+ "]";
	}
	
	
}
