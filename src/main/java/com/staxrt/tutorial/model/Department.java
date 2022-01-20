package com.staxrt.tutorial.model;

import javax.persistence.*;

@Entity
@Table(name = "Department")
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long deptId;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @Column(name = "number_of_employees")
    private long numberOfEmployees;

    public long getDeptId() { return deptId; }

    public void setDeptId(long deptId) { this.deptId = deptId; }

    public String getDeptName() { return deptName; }

    public void setDeptName(String deptName) { this.deptName = deptName; }

    public long getNumberOfEmployees() { return numberOfEmployees; }

    public void setNumberOfEmployees(long numberOfEmployees) { this.numberOfEmployees = numberOfEmployees; }

    @Override
    public String toString() {
        return "Department={" +
                "department Id=" + deptId +
                ", department name='" + deptName + '\'' +
                ", number of employees=" + numberOfEmployees +
                "}";
    }
}
