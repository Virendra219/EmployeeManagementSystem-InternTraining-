package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Employee;
import com.staxrt.tutorial.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Add employee
    @PostMapping("/add")
    public Employee addEmployee(@Valid @RequestBody Employee newEmployee) {
        return employeeService.addEmployee(newEmployee);
    }

    //Getting all Employees
    @GetMapping("/display/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    //Getting user by id
    @GetMapping("/display")
    public Employee getEmployeeById(@RequestParam(name = "empId") Long empId)
        throws ResourceNotFoundException {
        try {
            return employeeService.getEmployeeById(empId);
        }
        catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    // Update employee with given id
    @PutMapping("/update")
    public Employee updateEmployee(
            @RequestParam(name = "empId") Long empId, @Valid @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {
        try {
            return employeeService.updateEmployee(empId, employeeDetails);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    // Delete employee with given id
    @DeleteMapping("/delete{empId}")
    public Map<String, Boolean> deleteEmployee(@RequestParam(name = "empId") Long empId)
        throws ResourceNotFoundException {
        try {
            return employeeService.deleteEmployee(empId);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    // Display top n employees within a salary range
    @GetMapping("/display/top/n/in/salary/range")
    @ResponseBody
    public List<Employee> employeesInSalaryRange(@RequestParam(name = "n") Long n,
                                                 @RequestParam(name = "fromSalary") double fromSalary,
                                                 @RequestParam(name = "toSalary") double toSalary) {
        return employeeService.employeesInSalaryRange(fromSalary, toSalary, n);
    }

    // Display employee with max salary in each department
    @GetMapping("/display/max/salaries")
    @ResponseBody
    public List<String> departmentWiseMaxSalary() {
        return employeeService.departmentWiseMaxSalary();
    }

    // Display employees who have a detail matching a given pattern
    @GetMapping("display/pattern")
    public List<Employee> recordsHavingPattern(@RequestParam(name = "identifier") int identifier,
                                               @RequestParam(name = "pattern") String pattern) throws Exception {
        if (!(identifier>0 && identifier<4))
            throw new Exception("Invalid identifier! Use 1 with prefix, 2 with suffix and 3 with word.");
        return employeeService.recordHavingPattern(pattern, identifier);
    }
}
