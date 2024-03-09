package com.example.managementsystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.managementsystem.model.Employee;
import com.example.managementsystem.repository.EmployeeRepository;

@CrossOrigin(origins = "${cors.url}")
@RestController
@RequestMapping("api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeById(@PathVariable Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Map<String, Object>> deleteEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            employeeRepository.delete(employee);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Employee has been deleted successfully");
            response.put("code", 200);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Employee not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable Long employeeId,
            @RequestBody Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setEmailId(updatedEmployee.getEmailId());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Employee updated successfully");
            response.put("code", 200);
            response.put("employee", updatedEmployee);

            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Employee not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
