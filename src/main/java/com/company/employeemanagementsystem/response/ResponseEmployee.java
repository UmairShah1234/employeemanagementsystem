package com.company.employeemanagementsystem.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.company.employeemanagementsystem.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEmployee {

    private String error;
    private String message;
    private Boolean isError;
    private List<Employee> employees;
    private HttpStatus httpStatus;
}
