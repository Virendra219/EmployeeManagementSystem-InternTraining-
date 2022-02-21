package com.staxrt.tutorial.service;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.Department;
import com.staxrt.tutorial.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department addDepartment(Department department) { return departmentRepository.save(department); }

    public List<Department> getAllDepartment() { return departmentRepository.findAll(); }

    public Department getDepartmentById(Long id) throws ResourceNotFoundException {
        Department department = departmentRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No department with id = " + id));

        return department;
    }

    public Department updateDepartment(Long id, Department newDetails) throws ResourceNotFoundException {
        Department department = departmentRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No department with id = " + id));

        department.setDeptName(newDetails.getDeptName());
        department.setNumberOfEmployees(newDetails.getNumberOfEmployees());

        departmentRepository.save(department);
        return department;
    }

    public Map<String, Boolean> deleteDepartment(Long id) throws ResourceNotFoundException {
        Department department = departmentRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No department with id = " + id));

        departmentRepository.delete(department);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
