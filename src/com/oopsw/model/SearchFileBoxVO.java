package com.oopsw.model;

import java.util.Date;

public class SearchFileBoxVO {
	String fileName;
	String filePath;
	String projectName;
	String taskName;
	String taskNo;
	String projectNo;
	Date delDate;
	
	
	
	public SearchFileBoxVO() {}

	public SearchFileBoxVO(String fileName, String filePath, String projectName, String taskName, String taskNo,
			String projectNo, Date delDate) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.projectName = projectName;
		this.taskName = taskName;
		this.taskNo = taskNo;
		this.projectNo = projectNo;
		this.delDate = delDate;
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	@Override
	public String toString() {
		return "SearchFileBoxVO [fileName=" + fileName + ", filePath=" + filePath + ", projectName=" + projectName
				+ ", taskName=" + taskName + ", taskNo=" + taskNo + ", projectNo=" + projectNo + ", delDate=" + delDate
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delDate == null) ? 0 : delDate.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((projectNo == null) ? 0 : projectNo.hashCode());
		result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + ((taskNo == null) ? 0 : taskNo.hashCode());
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
		SearchFileBoxVO other = (SearchFileBoxVO) obj;
		if (delDate == null) {
			if (other.delDate != null)
				return false;
		} else if (!delDate.equals(other.delDate))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (projectNo == null) {
			if (other.projectNo != null)
				return false;
		} else if (!projectNo.equals(other.projectNo))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (taskNo == null) {
			if (other.taskNo != null)
				return false;
		} else if (!taskNo.equals(other.taskNo))
			return false;
		return true;
	}
	
	
    
}