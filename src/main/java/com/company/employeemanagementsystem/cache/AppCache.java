package com.company.employeemanagementsystem.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.employeemanagementsystem.model.Employee;
import com.company.employeemanagementsystem.repository.EmployeeRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AppCache {

    @Autowired
    private EmployeeRepository employeeRepository;

    List<Employee> employees = new ArrayList<>();
    List<String> employeeEmailList = new ArrayList<>();

    @PostConstruct
    void initialize() {
        employees = employeeRepository.findAll();
        employeeEmailList = employees.stream()
                .map(employee -> employee.getEmail())
                .toList();
    }

    public List<String> getAllEmployeesEmail() {
        return this.employeeEmailList;
    }

    public boolean isEmailPresent(String email){
        return employeeEmailList.contains(email);
    }
}
