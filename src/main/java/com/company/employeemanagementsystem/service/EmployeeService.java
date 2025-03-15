package com.company.employeemanagementsystem.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.company.employeemanagementsystem.cache.AppCache;
import com.company.employeemanagementsystem.model.Employee;
import com.company.employeemanagementsystem.repository.EmployeeRepository;
import com.company.employeemanagementsystem.request.RequestEmployee;
import com.company.employeemanagementsystem.response.ResponseEmployee;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppCache appCache;
    @Autowired
    private EntityManager entityManager;

    public ResponseEmployee createEmployee(RequestEmployee requestEmployee) {
        if (employeeRepository.existsByEmail(requestEmployee.getEmail())) {
            return mapToResponse("FAILURE", "EMAIL ALREADY EXISTS", null, true, HttpStatus.CONFLICT);
        }
        Employee employee = Employee.builder()
                .email(requestEmployee.getEmail())
                .userName(requestEmployee.getName())
                .department(requestEmployee.getDepartment())
                .position(requestEmployee.getPosition())
                .salary(requestEmployee.getSalary())
                .build();

        employeeRepository.save(employee);

        return mapToResponse("SUCCESS", "EMPLOYEE CREATED", List.of(employee), true, HttpStatus.CREATED);
    }

    public ResponseEmployee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty())
            return mapToResponse("FAILURE", "EMPLOYEE NOT FOUND", null, true, HttpStatus.NOT_FOUND);
        return mapToResponse("SUCCESS", "EMPLOYEE FETCHED", List.of(employee.get()), false, HttpStatus.OK);
    }

    public ResponseEmployee getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return mapToResponse("SUCCESS", "EMPLOYEE FETCHED", employees, false, HttpStatus.OK);
    }

    public ResponseEmployee updateEmplpyeeById(Long id, RequestEmployee requestEmployee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEmplpyeeById'");
    }

    private ResponseEmployee mapToResponse(String error, String message, List<Employee> employees, Boolean isError,
            HttpStatus httpStatus) {
        return ResponseEmployee.builder()
                .error(error)
                .isError(isError)
                .message(message)
                .employees(employees)
                .httpStatus(httpStatus).build();
    }

    @Transactional
    public ResponseEmployee createEmployees(MultipartFile file) {
        List<Employee> employees = new ArrayList<Employee>();
        List<String> recordWithError = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            boolean firstLine = true;
            String line;
            int batchSize = 1000;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(",");

                if (appCache.isEmailPresent(data[2])) {
                    recordWithError.add(data[0]);
                    continue;
                }
                Employee employee = new Employee();
                employee.setEmail(data[2]);
                employee.setDepartment(data[3]);
                employee.setPosition(data[4]);
                employee.setUserName(data[1]);
                employee.setSalary(new BigDecimal(data[5]));
                employee.setCreatedDate(new Date());
                entityManager.persist(employee);

                employees.add(employee);

                if (employees.size() % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
            if (employees.size() > 0) {
                entityManager.flush();
                entityManager.clear();
            } else
                throw new FileAlreadyExistsException("File Already Uploaded");
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
            return mapToResponse("FAILURE", "FILE ALREADY UPLOADED", employees, true, HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Processing Finished");
        return mapToResponse("SUCCESS", "FILE UPLOADED", employees, false, HttpStatus.CREATED);

    }

    public ResponseEmployee getAllEmployeesByDepartment(String department) {
        List<Employee> employees = employeeRepository.findByDepartment(department);
        return mapToResponse("SUCCESS", "EMPLOYEES FETCHED", employees, false, HttpStatus.OK);
    }

}
