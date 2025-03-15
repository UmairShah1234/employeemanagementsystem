package com.company.employeemanagementsystem.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.employeemanagementsystem.model.Employee;
import com.company.employeemanagementsystem.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setupEmployee() {
        employee = Employee.builder()
                .Id(1L)
                .createdDate(new Date())
                .department("IT")
                .salary(new BigDecimal(100000))
                .position("Software Developer")
                .userName("Umair Shah")
                .email("umair123@gmail.com")
                .build();
    }

    @Test
    void testCreateEmployee(){
        // when(employeeRepository.save(null))
    }

}
