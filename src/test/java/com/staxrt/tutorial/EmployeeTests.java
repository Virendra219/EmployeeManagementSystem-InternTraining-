package com.staxrt.tutorial;

import com.staxrt.tutorial.model.Employee;
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

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() { return "http://localhost:" + port; }

    @Test
    public void contextLoads() { }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee(Long.valueOf(100),
                "admin",
                "admin",
                "admin",
                Long.valueOf(10000),
                "admin@gmail.com",
                "admin",
                Long.valueOf(9999999999L),
                new Date(0000-00-00));

        ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/employee/add", employee, Employee.class);
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }
    @Test
    public void testGetAllEmployees() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/employee/display/all",
                HttpMethod.GET, entity, String.class);

        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = restTemplate.getForObject(getRootUrl() + "/employee/display?empId=1", Employee.class);
        System.out.println(employee.getFirstName());
        Assert.assertNotNull(employee);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = restTemplate.getForObject(getRootUrl() + "/employee/display?empId=1", Employee.class);
        employee.setFirstName("admin1");
        employee.setLastName("admin2");

        restTemplate.put(getRootUrl() + "/employee/update?1", employee);

        Employee updatedEmployee = restTemplate.getForObject(getRootUrl() + "/employee/display?1", Employee.class);
        Assert.assertNotNull(updatedEmployee);
    }

    @Test
    public void testDeleteEmployee() {
        int id = 2;
        Employee employee = restTemplate.getForObject(getRootUrl() + "/employee/display?1", Employee.class);
        Assert.assertNotNull(employee);

        restTemplate.delete(getRootUrl() + "/employee/delete?1");

        try {
            employee = restTemplate.getForObject(getRootUrl() + "/employee/display?1", Employee.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
