package com.oopsw.model;

import java.util.Date;

public class ScheduleVO {

	private int scheduleNo;
	private String employeeId;
	private Date scheduleDate;
	private String scheduleType;
	private String title;
	private Date startTime;
	private Date endTime;
	
	public ScheduleVO(){}
	
	public ScheduleVO(int scheduleNo, String employeeId, Date scheduleDate, String scheduleType, String title,
			Date startTime, Date endTime) {
		setScheduleNo(scheduleNo);
		setEmployeeId(employeeId);
		setScheduleDate(scheduleDate);
		setScheduleType(scheduleType);
		setTitle(title);
		setStartTime(startTime);
		setEndTime(endTime);
	}
	
	public ScheduleVO(String employeeId, Date scheduleDate, String scheduleType, String title,
			Date startTime, Date endTime) {
		setEmployeeId(employeeId);
		setScheduleDate(scheduleDate);
		setScheduleType(scheduleType);
		setTitle(title);
		setStartTime(startTime);
		setEndTime(endTime);
	}

	public int getScheduleNo() {
		return scheduleNo;
	}
	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "ScheduleVO [scheduleNo=" + scheduleNo + ", employeeId=" + employeeId + ", scheduleDate=" + scheduleDate
				+ ", scheduleType=" + scheduleType + ", title=" + title + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}
	
	
}
