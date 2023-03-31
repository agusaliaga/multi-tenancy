package com.mindgeek.multitenancy.controllers;

import com.mindgeek.multitenancy.repositories.EmployeeRepository;
import com.mindgeek.multitenancy.domain.Employee;
import com.mindgeek.multitenancy.dtos.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = new Employee();
        newEmployee.setName(employeeDTO.getName());
        newEmployee.setAge(employeeDTO.getAge());
        employeeRepository.save(newEmployee);

        return ResponseEntity.ok(newEmployee);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return ResponseEntity.ok(employees);
    }
}
