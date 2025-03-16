package com.company.employeemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.employeemanagementsystem.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
