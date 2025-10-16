package com.oopsw.model;

import java.sql.Timestamp;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ScheduleVO {

	private int scheduleNo;
	private String employeeId;
	private Date scheduleDate;
	private Date startTime;
	private Date endTime;
	private String title;
	private String scheduleType;

	public ScheduleVO() {
	}

	public ScheduleVO(int scheduleNo, String employeeId, Date scheduleDate, Date startTime, Date endTime, String title,
			String scheduleType) {
		super();
		this.scheduleNo = scheduleNo;
		this.employeeId = employeeId;
		this.scheduleDate = scheduleDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.title = title;
		this.scheduleType = scheduleType;
	}

	public ScheduleVO(String employeeId, java.sql.Date scheduleDate, String scheduleType, String title,
			Timestamp startTime, Timestamp endTime) {
		this.employeeId = employeeId;
		this.scheduleDate = scheduleDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.title = title;
		this.scheduleType = scheduleType;
	}

	public ScheduleVO(int scheduleNo, String employeeId, java.sql.Date scheduleDate, String scheduleType, String title,
			Timestamp startTime, Timestamp endTime) {
		this.scheduleNo = scheduleNo;
		this.employeeId = employeeId;
		this.scheduleDate = scheduleDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.title = title;
		this.scheduleType = scheduleType;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	@Override
	public String toString() {
		return "ScheduleVO [scheduleNo=" + scheduleNo + ", employeeId=" + employeeId + ", scheduleDate=" + scheduleDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", title=" + title + ", scheduleType="
				+ scheduleType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((scheduleDate == null) ? 0 : scheduleDate.hashCode());
		result = prime * result + scheduleNo;
		result = prime * result + ((scheduleType == null) ? 0 : scheduleType.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ScheduleVO other = (ScheduleVO) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (scheduleDate == null) {
			if (other.scheduleDate != null)
				return false;
		} else if (!scheduleDate.equals(other.scheduleDate))
			return false;
		if (scheduleNo != other.scheduleNo)
			return false;
		if (scheduleType == null) {
			if (other.scheduleType != null)
				return false;
		} else if (!scheduleType.equals(other.scheduleType))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
