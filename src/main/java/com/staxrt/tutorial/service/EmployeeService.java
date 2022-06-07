package com.staxrt.tutorial.service;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Employee;
import com.staxrt.tutorial.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    //private Object[] record ;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) { this.employeeRepository = employeeRepository; }

    // method to add new employee
    public long addEmployee(Employee newEmployee) {
        employeeRepository.save(newEmployee);
        return newEmployee.getEmpId();
    }

    // method to return all employees details
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // method to return employee by Id
    public Employee getEmployeeById(Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No employee has id = " + id));
        return employee;
    }

    // method to update employee details
    public Employee updateEmployee(Long id, Employee newDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No employee has id = " + id));

        employee.setFirstName(newDetails.getFirstName());
        employee.setLastName(newDetails.getLastName());
        employee.setEmailId(newDetails.getEmailId());
        employee.setAddress(newDetails.getAddress());
        employee.setDeptName(newDetails.getDeptName());
        employee.setSalary(newDetails.getSalary());
        employee.setPhone(newDetails.getPhone());
        employee.setDateOfJoining(newDetails.getDateOfJoining());

        employeeRepository.save(employee);
        return employee;
    }

    // method to delete employee details
    public Map<String, Boolean> deleteEmployee(Long empId) throws ResourceNotFoundException {
        Employee employee = employeeRepository
                .findById(empId)
                .orElseThrow(()->new ResourceNotFoundException("No employee has id = " + empId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public List<Employee> employeesInSalaryRange(double fromSalary, double toSalary, Long n)
    {
        List<Object[]> employeesRaw = employeeRepository.employeesInSalaryRange(fromSalary, toSalary, n);
        List<Employee> employees = new ArrayList<>();
        for (Object[] record:employeesRaw)
        {
            Long id = Long.parseLong(record[0].toString());
            System.out.println(id);
            Employee employee = new Employee(
                    record[1].toString(),
                    record[2].toString(),
                    record[3].toString(),
                    Long.parseLong(record[4].toString()),
                    record[5].toString(),
                    record[6].toString(),
                    Long.parseLong(record[7].toString()),
                    Date.valueOf(record[8].toString()));
            employees.add(employee);
        }
        return employees;
    }

    public List<String> departmentWiseMaxSalary() {
        List<Object[]> records=employeeRepository.departmentWiseMaxSalary();
        List<String> response = new ArrayList<>();
        for (Object[] record:records) {
            String output = "{department:'" + record[0] + '\'' + ", salary=" + record[1] + "}";
            response.add(output);
        }
        return response;
    }

    public List<Employee> recordHavingPattern(String pattern, int id) {
        List<Object[]> records;
        if (id==1) {
            records = employeeRepository.findByPrefix(pattern);
        } else if (id==2) {
            records = employeeRepository.findBySuffix(pattern);
        } else {
            records = employeeRepository.findByPattern(pattern);
        }
        List<Employee> output = new ArrayList<>();
        for (Object[] record:records)
        {
            Employee employee = new Employee(
                    record[1].toString(),
                    record[2].toString(),
                    record[3].toString(),
                    Long.parseLong(record[4].toString()),
                    record[5].toString(),
                    record[6].toString(),
                    Long.parseLong(record[7].toString()),
                    Date.valueOf(record[8].toString()));
            output.add(employee);
        }
        return output;
    }
}
