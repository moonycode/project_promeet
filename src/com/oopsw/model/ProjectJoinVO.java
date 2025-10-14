package com.oopsw.model;

import java.util.Date;

public class ProjectJoinVO {
	// 사원 정보(employees)
	private String employeeId;
	private String name;
	private String position;
	private String department;
    private String phoneNumber;
    private String email;
    
    // 프로젝트 정보(projects)
    private String managerId; 
    private int projectNo;
    
    // 참여 정보(project_join)
	private int projectJoinNo;
	
	
	private Date inDate;
	private Date upDate;
	private int joinFlag;
	
	
	public ProjectJoinVO(){}
	public ProjectJoinVO(
            int projectJoinNo,
            String employeeId,
            String name,
            String position,
            String department,
            String phoneNumber,
            String email,
            int projectNo,
            String managerId,
            Date inDate,
            Date upDate,
            int joinFlag
    ){
        setProjectJoinNo(projectJoinNo);
        setEmployeeId(employeeId);
        setName(name);
        setPosition(position);
        setDepartment(department);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setProjectNo(projectNo);
        setManagerId(managerId);
        setInDate(inDate);
        setUpDate(upDate);
        setJoinFlag(joinFlag);
    }

    // ===== getters / setters =====
    public int getProjectJoinNo() { return projectJoinNo; }
    public void setProjectJoinNo(int projectJoinNo) { this.projectJoinNo = projectJoinNo; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public int getProjectNo() { return projectNo; }
    public void setProjectNo(int projectNo) { this.projectNo = projectNo; }

    public Date getInDate() { return inDate; }
    public void setInDate(Date inDate) { this.inDate = inDate; }

    public Date getUpDate() { return upDate; }
    public void setUpDate(Date upDate) { this.upDate = upDate; }

    public int getJoinFlag() { return joinFlag; }
    public void setJoinFlag(int joinFlag) { this.joinFlag = joinFlag; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    @Override
    public String toString() {
        return "ProjectJoinVO{" +
                "projectJoinNo=" + projectJoinNo +
                ", employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", projectNo=" + projectNo +
                ", managerId='" + managerId + '\'' +
                ", inDate=" + inDate +
                ", upDate=" + upDate +
                ", joinFlag=" + joinFlag +
                '}';
    }
}