package com.oopsw.model;

import java.util.Date;

public class ProjectJoinVO {
	private String employeeId;
	private String name;
	private String position;
	private String department;
    private String phoneNumber;
    private String email;
    
    private int projectNo;
    
	private int projectJoinNo;
	private Date inDate;
	private Date dateUp;
	private int joinFlag;
	private int managerFlag;
	
	
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
            Date inDate,
            Date dateUp,
            int joinFlag, 
            int managerFlag
    ){
        setProjectJoinNo(projectJoinNo);
        setEmployeeId(employeeId);
        setName(name);
        setPosition(position);
        setDepartment(department);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setProjectNo(projectNo);
        setInDate(inDate);
        setDateUp(dateUp);
        setJoinFlag(joinFlag);
        setManagerFlag(managerFlag);
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

    public Date getDateUp() { return dateUp; }
    public void setDateUp(Date dateUp) { this.dateUp = dateUp; }

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

    public int getManagerFlag() {
		return managerFlag;
	}
	public void setManagerFlag(int managerFlag) {
		this.managerFlag = managerFlag;
	}
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
                ", inDate=" + inDate +
                ", dateUp=" + dateUp +
                ", joinFlag=" + joinFlag +
                ", managerFlag=" + managerFlag +
                '}';
    }
}