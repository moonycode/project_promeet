package com.oopsw.model;

import java.util.Date;

public class ProjectVO {
	
	private int projectNo;
	private String creatorId;
	private String projectName;
	private String client;
	private Date startDate;
	private Date endDate;
	private Date inDate;
	private Date dateUp; //upDate=>dateUp //db upDate라는 메서드가 있음
	private Date completeDate;
	private String description;
	private Date delDate;
	private int memberCount;
	private int totalCnt;
	private int doneCnt;
	private int dday;
	
	
	public ProjectVO(){}
	public ProjectVO(int projectNo, String creatorId, String projectName, String client, Date startDate, Date endDate,
			Date inDate, Date dateUp, Date completeDate, String description, Date delDate, int memberCount, int totalCnt, int doneCnt,
			int dday) {
		setProjectName(projectName);
		setCreatorId(creatorId);
		setProjectName(projectName);
		setClient(client);
		setStartDate(startDate);
		setEndDate(endDate);
		setInDate(inDate);
		setDateUp(dateUp);
		setCompleteDate(completeDate);
		setDescription(description);
		setDelDate(delDate);
		setMemberCount(memberCount);
		setTotalCnt(totalCnt);
		setDoneCnt(doneCnt);
		setDday(dday);
	}

	public int getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(int projectNo) {
		this.projectNo = projectNo;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getDoneCnt() {
		return doneCnt;
	}
	public void setDoneCnt(int doneCnt) {
		this.doneCnt = doneCnt;
	}
	public int getDday() {
		return dday;
	}
	public void setDday(int dday) {
		this.dday = dday;
	}
	
	@Override
	public String toString() {
		return "ProjectVO [projectNo=" + projectNo + ", creatorId=" + creatorId + ", projectName=" + projectName
				+ ", client=" + client + ", startDate=" + startDate + ", endDate=" + endDate + ", inDate=" + inDate
				+ ", dateUp=" + dateUp + ", completeDate=" + completeDate + ", description=" + description + ", delDate=" + delDate 
				+ ", memberCount=" + memberCount + ", totalCnt=" + totalCnt + ", doneCnt=" + doneCnt + ", dday=" + dday + "]";
	}
}