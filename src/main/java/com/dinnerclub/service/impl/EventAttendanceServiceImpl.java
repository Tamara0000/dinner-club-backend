package com.dinnerclub.service.impl;

import com.dinnerclub.entity.EventAttendance;
import com.dinnerclub.repository.EventAttendanceRepository;
import com.dinnerclub.service.EventAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventAttendanceServiceImpl implements EventAttendanceService {
    private final EventAttendanceRepository eventAttendanceRepository;

}
