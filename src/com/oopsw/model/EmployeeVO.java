package com.oopsw.model;

import java.util.Date;

public class EmployeeVO {

	private String employeeId;
	private String name;
	private String department;
	private String phoneNumber;
	private String email;
	private String password;
	private String workStatus;
	private String position;
	private Date inDate;
	private Date upDate;
	
	private Date resignDate;
	private int projectLeader; // char->int
	
	public EmployeeVO(){}
	public EmployeeVO(String employeeId, String name, String department, String phoneNumber, String email,
			String password, String workStatus, String position, Date inDate, Date upDate, Date resignDate,
			int projectLeader) {
		setEmployeeId(employeeId);
		setName(name);
		setDepartment(department);
		setPhoneNumber(phoneNumber);
		setEmail(email);
		setPassword(password);
		setWorkStatus(workStatus);
		setPosition(position);
		setInDate(inDate);
		setUpDate(upDate);
		setResignDate(resignDate);
		setProjectLeader(projectLeader); 
	}

	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public Date getResignDate() {
		return resignDate;
	}
	public void setResignDate(Date resignDate) {
		this.resignDate = resignDate;
	}
	public int getProjectLeader() {
		return projectLeader;
	}
	public void setProjectLeader(int projectLeader) {
		this.projectLeader = projectLeader;
	}

	@Override
	public String toString() {
		return "EmployeeVO [employeeId=" + employeeId + ", name=" + name + ", department=" + department
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", workStatus=" + workStatus + ", position="
				+ position + ", inDate=" + inDate + ", upDate=" + upDate + ", resignDate=" + resignDate
				+ ", projectLeader=" + projectLeader + "]";
	}
	
	
	
}
