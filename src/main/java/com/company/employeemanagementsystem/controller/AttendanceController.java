package com.company.employeemanagementsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.company.employeemanagementsystem.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/login/{id}")
    public String login(@PathVariable Long id) {
        return attendanceService.logIn(id);
    }

    @PostMapping("/logout/{id}")
    public String logout(@PathVariable Long id) {
        return attendanceService.logOut(id);
    }
    

    @GetMapping("/check-attendance/{id}")
    public String checkAttendanceForSameDay(@PathVariable Long id) {
        return attendanceService.getSameDayAttendance(id);
    }
    
}
