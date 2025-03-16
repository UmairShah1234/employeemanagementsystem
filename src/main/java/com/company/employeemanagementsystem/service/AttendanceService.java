package com.company.employeemanagementsystem.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.company.employeemanagementsystem.cache.AppCache;

@Service
public class AttendanceService {

    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final String ATTENDANCE_KEY = "attendance~";

    public String logIn(Long employeeId) {
        if (employeeId == null || !appCache.isEmployeeIdPresent(employeeId)) {
            return "INVALID ID";
        }

        if (redisTemplate.opsForValue().get(generateKey(employeeId)) != null)
            return "ALREADY LOGGED IN";

        redisTemplate.opsForValue().set(generateKey(employeeId), generateCheckInAndOutTime(), 1, TimeUnit.DAYS);
        return "LOGGED IN";

    }

    public String logOut(Long id) {
        String value = redisTemplate.opsForValue().get(generateKey(id));
        if (value.split("~").length > 1)
            return "ALREADY LOGGED OUT";
        value = redisTemplate.opsForValue().get(generateKey(id)) + "~" + generateCheckInAndOutTime();
        redisTemplate.opsForValue().set(generateKey(id), value);
        return "LOGGED OUT";
    }

    public String getSameDayAttendance(Long employeeId) {
        return redisTemplate.opsForValue().get(generateKey(employeeId));
    }

    private String generateKey(Long employeeId) {
        LocalDate date = LocalDate.now();
        return ATTENDANCE_KEY + date + "~" + employeeId;
    }

    private String generateCheckInAndOutTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }
}
