package com.company.employeemanagementsystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.company.employeemanagementsystem.model.Employee;
import com.company.employeemanagementsystem.request.RequestEmployee;
import com.company.employeemanagementsystem.response.ResponseEmployee;
import com.company.employeemanagementsystem.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;
    private Employee employee;

    @BeforeEach
    void createEmployee() {
        // employee = Employee.builder()
        // .Id(null)
    }

    @Test
    void testCreateEmployee() throws Exception {
        when(employeeService.createEmployee(any(RequestEmployee.class))).thenReturn(getResponseEmployeeForSuccess());

        mockMvc.perform(post("/api/employee/create-employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getRequestEmployee())))
                .andExpect(status().isCreated());

    }

    private RequestEmployee getRequestEmployee() {
        return RequestEmployee.builder()
                .email("umair@gmail.com")
                .name("Umair")
                .department("IT")
                .position("Software Developer")
                .salary(new BigDecimal(100000))
                .build();
    }

    private ResponseEmployee getResponseEmployeeForSuccess() {
        return ResponseEmployee.builder()
                .error("SUCCESS")
                .message("EMPLOYEE CREATED")
                .isError(false)
                .employees(null)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Test
    void testGetAllEmployees() {

    }

    @Test
    void testGetEmployeeById() {

    }

    @Test
    void testUpdateEmployeeById() {

    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        public EmployeeService employeeService() {
            return mock(EmployeeService.class);  // Creates a mock instance
        }
    }
}
