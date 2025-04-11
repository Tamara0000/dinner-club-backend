package com.dinnerclub.service;


import com.dinnerclub.entity.EventSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventScheduleService {
    //this service can also be implemented as Page if needed for optimization
    List<EventSchedule> findAll();
    List<EventSchedule> findAllUpcomingEvents();
    List<EventSchedule> findAllCancelledEvents();
    List<EventSchedule> findAllCompletedEvents();

    //same logic as three services above but optimized for faster loading if the database is large
    Page<EventSchedule> findAllUpcomingEvents(Pageable page);
    Page<EventSchedule> findAllCancelledEvents(Pageable page);
    Page<EventSchedule> findAllCompletedEvents(Pageable page);
    EventSchedule update(EventSchedule eventSchedule);
}
