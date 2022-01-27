package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query (value = "Select *" +
            " from Employee " +
            "where salary>=?1 and salary<=?2 " +
            "limit ?3", nativeQuery = true)
    public List<Object[]> employeesInSalaryRange(double minSalary, double maxSalary, Long n);

    @Query ("Select deptName as department, MAX(salary) as salary" +
            " from Employee " +
            "group by deptName")
    public List<Object[]> departmentWiseMaxSalary();

    @Query (value = "Select * From Employee " +
            "where first_name like ?1% or last_name like ?1% or dept_name like ?1% " +
            "or email_id like ?1% or address like ?1%", nativeQuery = true)
    public List<Object[]> findByPrefix(String pattern);

    @Query (value = "Select * From Employee " +
            "where first_name like %?1 or last_name like %?1 or dept_name like %?1 " +
            "or email_id like %?1 or address like %?1", nativeQuery = true)
    public List<Object[]> findBySuffix(String pattern);

    @Query (value = "Select * From Employee " +
            "where first_name = ?1 or last_name = ?1 or dept_name = ?1 " +
            "or email_id = ?1 or address = ?1", nativeQuery = true)
    public List<Object[]> findByPattern(String pattern);
}
