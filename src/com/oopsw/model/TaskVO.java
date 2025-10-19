package com.oopsw.model;

import java.util.Date;

public class TaskVO {

    private int taskNo;
    private int projectNo;
    private String taskName;
    private String taskStatus;
    private Date startDate;
    private Date endDate;
    private Date inDate;
    private Date dateUp;
    private String priority;
    private Date delDate;

    private String membersText;
    private int progress;

    public TaskVO(){}

    public TaskVO(int taskNo, int projectNo, String taskName, String taskStatus,
                  Date startDate, Date endDate, Date inDate, Date dateUp,
                  String priority, Date delDate, String membersText, int progress){
        setTaskNo(taskNo);
        setProjectNo(projectNo);
        setTaskName(taskName);
        setTaskStatus(taskStatus);
        setStartDate(startDate);
        setEndDate(endDate);
        setInDate(inDate);
        setDateUp(dateUp);
        setPriority(priority);
        setDelDate(delDate);
        setMembersText(membersText);
        setProgress(progress);
    }

    public int getTaskNo(){ return taskNo; }
    public void setTaskNo(int taskNo){ this.taskNo = taskNo; }
    public int getProjectNo(){ return projectNo; }
    public void setProjectNo(int projectNo){ this.projectNo = projectNo; }
    public String getTaskName(){ return taskName; }
    public void setTaskName(String taskName){ this.taskName = taskName; }
    public String getTaskStatus(){ return taskStatus; }
    public void setTaskStatus(String taskStatus){ this.taskStatus = taskStatus; }
    public Date getStartDate(){ return startDate; }
    public void setStartDate(Date startDate){ this.startDate = startDate; }
    public Date getEndDate(){ return endDate; }
    public void setEndDate(Date endDate){ this.endDate = endDate; }
    public Date getInDate(){ return inDate; }
    public void setInDate(Date inDate){ this.inDate = inDate; }
    public Date getDateUp(){ return dateUp; }
    public void setDateUp(Date dateUp){ this.dateUp = dateUp; }
    public String getPriority(){ return priority; }
    public void setPriority(String priority){ this.priority = priority; }
    public Date getDelDate(){ return delDate; }
    public void setDelDate(Date delDate){ this.delDate = delDate; }
    public String getMembersText(){ return membersText; }
    public void setMembersText(String membersText){ this.membersText = membersText; }
    public int getProgress(){ return progress; }
    public void setProgress(int progress){ this.progress = progress; }

	@Override
	public String toString() {
		return "TaskVO [taskNo=" + taskNo + ", projectNo=" + projectNo + ", taskName=" + taskName + ", taskStatus="
				+ taskStatus + ", startDate=" + startDate + ", endDate=" + endDate + ", inDate=" + inDate + ", dateUp="
				+ dateUp + ", priority=" + priority + ", delDate=" + delDate + ", membersText=" + membersText
				+ ", progress=" + progress + "]";
	}

    }

