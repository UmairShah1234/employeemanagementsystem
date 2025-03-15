package com.company.employeemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.employeemanagementsystem.request.RequestEmployee;
import com.company.employeemanagementsystem.response.ResponseEmployee;
import com.company.employeemanagementsystem.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create-employee")
    public ResponseEntity<ResponseEmployee> createEmployee(@RequestBody RequestEmployee requestEmployee) {
        ResponseEmployee employee = employeeService.createEmployee(requestEmployee);
        return new ResponseEntity<>(employee, employee.getHttpStatus());
    }

    @PostMapping("/create-employees")
    public ResponseEntity<ResponseEmployee> postMethodName(@RequestParam("file") MultipartFile file) {
        ResponseEmployee employee = employeeService.createEmployees(file);
        return new ResponseEntity<>(employee, employee.getHttpStatus());
    }

    @GetMapping("/get-employee-by-id/{id}")
    public ResponseEntity<ResponseEmployee> getEmployeeById(@PathVariable Long id) {
        ResponseEmployee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, employee.getHttpStatus());
    }

    @GetMapping("/get-all-employees")
    public ResponseEntity<ResponseEmployee> getAllEmployees() {
        ResponseEmployee employee = employeeService.getAllEmployees();
        return new ResponseEntity<>(employee, employee.getHttpStatus());
    }

    @GetMapping("/get-all-employees-by-department")
    public ResponseEntity<ResponseEmployee> getAllEmployeesByDepartment(@RequestParam String department) {
        ResponseEmployee employee = employeeService.getAllEmployeesByDepartment(department);
        return new ResponseEntity<>(employee, employee.getHttpStatus());
    }
    

    @PutMapping("/update-employee-by-id/{id}")
    public ResponseEntity<ResponseEmployee> updateEmployeeById(@PathVariable Long id,
            @RequestBody RequestEmployee requestEmployee) {
        ResponseEmployee employee = employeeService.updateEmplpyeeById(id, requestEmployee);
        return new ResponseEntity<>(employee, employee.getHttpStatus());
    }
}
