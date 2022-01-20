package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Department;
import com.staxrt.tutorial.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Adding new department
    @PostMapping("/department")
    public Department addDepartment(@Valid @RequestBody Department newDepartment) {
        return departmentRepository.save(newDepartment);
    }

    // Getting all departments
    @GetMapping("/department")
    public List<Department> getAllDepartments() { return departmentRepository.findAll(); }

    // Getting department by Id
    @GetMapping("/department/{deptId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable(name = "deptId") Long deptId)
            throws ResourceNotFoundException {
        Department department =
                departmentRepository
                .findById(deptId)
                .orElseThrow(()-> new ResourceNotFoundException("No department having Id = " + deptId));
        return ResponseEntity.ok().body(department);
    }

    // Update Department with given Id
    @PutMapping("/department/{deptId}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable(name = "deptId") Long deptId,
            @Valid @RequestBody Department newDetails)
            throws ResourceNotFoundException {
        Department department =
                departmentRepository
                .findById(deptId)
                .orElseThrow(()->new ResourceNotFoundException("No department with Id = " + deptId));

        department.setDeptName(newDetails.getDeptName());
        department.setNumberOfEmployees(newDetails.getNumberOfEmployees());
        final Department updatedDept = departmentRepository.save(department);
        return ResponseEntity.ok(updatedDept);
    }

    // Delete department having given Id
    @DeleteMapping("/department/{deptId}")
    public Map<String, Boolean> deleteDepartment(@PathVariable(name = "deptId") Long deptId)
            throws ResourceNotFoundException {
        Department department =
                departmentRepository
                .findById(deptId)
                .orElseThrow(()-> new ResourceNotFoundException("No departmenet with id = " + deptId));

        departmentRepository.delete(department);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
