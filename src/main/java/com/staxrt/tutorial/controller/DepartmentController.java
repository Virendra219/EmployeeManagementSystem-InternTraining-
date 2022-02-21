package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Department;
import com.staxrt.tutorial.repository.DepartmentRepository;
import com.staxrt.tutorial.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Adding new department
    @PostMapping("/add")
    public Department addDepartment(@Valid @RequestBody Department newDepartment) { return departmentService.addDepartment(newDepartment); }

    // Getting all departments
    @GetMapping("/display/all")
    public List<Department> getAllDepartments() { return departmentService.getAllDepartment(); }

    // Getting department by Id
    @GetMapping("/display")
    public Department getDepartmentById(@RequestParam(name = "deptId") Long deptId)
            throws ResourceNotFoundException {
        try {
            return departmentService.getDepartmentById(deptId);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    // Update Department with given Id
    @PutMapping("/update")
    public Department updateDepartment(
            @RequestParam(name = "deptId") Long deptId,
            @Valid @RequestBody Department newDetails)
            throws ResourceNotFoundException {
        try {
            return departmentService.updateDepartment(deptId, newDetails);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    // Delete department having given Id
    @DeleteMapping("/department/{deptId}")
    public Map<String, Boolean> deleteDepartment(@PathVariable(name = "deptId") Long deptId)
            throws ResourceNotFoundException {
        try {
            return departmentService.deleteDepartment(deptId);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }
}
