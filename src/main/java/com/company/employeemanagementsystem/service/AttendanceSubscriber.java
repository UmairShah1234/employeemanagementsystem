package com.company.employeemanagementsystem.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttendanceSubscriber extends AttendanceDumper implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String[] msg = message.toString().split("~");
        String employee = msg[0];
        String time = msg[1];
        if (msg.length > 2)
            updateAttendance(Long.valueOf(employee), msg[2]);
        else
            saveAttendance(Long.parseLong(employee), time);
    }

}
