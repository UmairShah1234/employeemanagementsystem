package com.company.employeemanagementsystem.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.employeemanagementsystem.model.Attendance;
import com.company.employeemanagementsystem.repository.AttendanceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AttendanceDumper {

    @Autowired
    private AttendanceRepository attendanceRepository;

    void saveAttendance(Long employeeId, String time) {
        attendanceRepository.save(Attendance.builder()
                .date(LocalDate.now())
                .employeeId(employeeId)
                .inTime(createTime(time))
                .build());
    }

    void updateAttendance(Long employeeId, String time) {
        Optional<Attendance> attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());
        if (attendance.isEmpty()) {
            log.info("ERROR");
            return;
        }
        attendance.get().setOutTime(createTime(time));
        attendanceRepository.save(attendance.get());
    }

    private LocalTime createTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
