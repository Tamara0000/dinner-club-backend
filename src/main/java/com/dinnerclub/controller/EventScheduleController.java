package com.dinnerclub.controller;

import com.dinnerclub.entity.EventSchedule;
import com.dinnerclub.service.EventScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event-schedule")
public class EventScheduleController {
    private final EventScheduleService eventScheduleService;

    @Operation(summary = "Update and mail notification about the change.")
    @PutMapping("/update")
    public ResponseEntity<EventSchedule> updateEventSchedule(@RequestBody EventSchedule eventSchedule) {
        return ResponseEntity.ok(eventScheduleService.update(eventSchedule));
    }
    @Operation(summary = "All upcoming events.")
    @GetMapping("/all-upcoming-events")
    public ResponseEntity<List<EventSchedule>> getAllUpcomingEvents() {
        return ResponseEntity.ok(eventScheduleService.findAllUpcomingEvents());
    }
    @Operation(summary = "All cancelled events.")
    @GetMapping("/all-cancelled-events")
    public ResponseEntity<List<EventSchedule>> getAllCancelledEvents() {
        return ResponseEntity.ok(eventScheduleService.findAllCancelledEvents());
    }
    @Operation(summary = "All completed events.")
    @GetMapping("/all-completed-events")
    public ResponseEntity<List<EventSchedule>> getAllCompletedEvents() {
        return ResponseEntity.ok(eventScheduleService.findAllCompletedEvents());
    }

    @Operation(summary = "All events.")
    @GetMapping("/all-events")
    public ResponseEntity<List<EventSchedule>> getAllEvents() {
        return ResponseEntity.ok(eventScheduleService.findAll());
    }

}
