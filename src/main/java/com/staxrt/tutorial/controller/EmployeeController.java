package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Employee;
import com.staxrt.tutorial.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Add employee
    @PostMapping("/employee")
    public Employee addEmployee(@Valid @RequestBody Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    //Getting all Employees
    @GetMapping("/employee")
    public List<Employee> getAllEmployees() { return employeeRepository.findAll(); }

    //Getting user by id
    @GetMapping("/employee/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "empId") Long empId)
        throws ResourceNotFoundException {
        Employee employee =
                employeeRepository
                .findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id = " + empId + " doesn't exist."));
        return ResponseEntity.ok().body(employee);
    }

    // Update employee with given id
    @PutMapping("/employee/{empId}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable(value = "empId") Long empId, @Valid @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {
        Employee employee =
                employeeRepository
                        .findById(empId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee with id = " + empId + " doesn't exist."));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setAddress(employeeDetails.getAddress());
        employee.setDeptName(employeeDetails.getDeptName());
        employee.setPhone(employeeDetails.getPhone());
        employee.setDateOfJoining(employeeDetails.getDateOfJoining());
        final Employee updatedEmp = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmp);
    }

    //Delete employee with given id
    @DeleteMapping("/employee/{empId}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "empId") Long empId)
        throws ResourceNotFoundException {
        Employee employee =
                employeeRepository
                .findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id = " + empId + " doesn't exist."));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
