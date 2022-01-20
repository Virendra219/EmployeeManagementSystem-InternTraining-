package com.staxrt.tutorial.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Employee")
@EntityListeners(AuditingEntityListener.class)
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long empId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "dept", nullable = false)
    private String deptName;

    @Column(name = "email_id", nullable = false)
    private String emailId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private long phone;

    @Column(name = "date_of_joining", nullable = false)
    private Date dateOfJoining;

    public long getEmpId() { return empId; }

    public void setEmpId(long empId) { this.empId = empId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDeptName() { return deptName; }

    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getEmailId() { return emailId; }

    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public long getPhone() { return phone; }

    public void setPhone(long phone) { this.phone = phone; }

    public Date getDateOfJoining() { return dateOfJoining; }

    public void setDateOfJoining(Date dateOfJoining) { this.dateOfJoining = dateOfJoining; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + deptName + '\'' +
                ", email='" + emailId + '\'' +
                ", address='" + address + '\'' +
                ", Phone=" + phone +
                ", date of joining=" + dateOfJoining +
                '}';
    }
}
