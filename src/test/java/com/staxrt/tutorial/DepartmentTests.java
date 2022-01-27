package com.staxrt.tutorial;

import com.staxrt.tutorial.model.Department;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() { return "http://localhost" + port; }

    @Test
    public void contextLoads() { }

    @Test
    public void testAddDepartment() {
        Department department = new Department();
        department.setDeptName("admin");
        department.setNumberOfEmployees(Long.valueOf(500L));

        ResponseEntity<Department> postResponse = restTemplate.postForEntity(getRootUrl() + "/department", department, Department.class);
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }

    @Test
    public void testGetAllDepartments() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null ,headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/department",
                HttpMethod.GET, entity, String.class);

        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testGetDepartmentById() {
        Department department = restTemplate.getForObject(getRootUrl() + "/department/1", Department.class);
        System.out.println(department.getDeptName());
        Assert.assertNotNull(department);
    }

    @Test
    public void testUpdateDepartment() {
        Department department = restTemplate.getForObject(getRootUrl() + "/department/1", Department.class);
        department.setDeptName("admin1");
        department.setNumberOfEmployees(Long.valueOf(1000L));

        restTemplate.put(getRootUrl() + "/department/1", department);

        Department updatedDepartment = restTemplate.getForObject(getRootUrl() + "/department/1", Department.class);
        Assert.assertNotNull(updatedDepartment);
    }

    @Test
    public void testDeleteDepartment() {
        Department department = restTemplate.getForObject(getRootUrl() + "/department/1", Department.class);
        Assert.assertNotNull(department);

        restTemplate.delete(getRootUrl() + "/department/1");
        try {
            department = restTemplate.getForObject(getRootUrl() + "/department/1", Department.class);
        } catch (final HttpClientErrorException e){
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
