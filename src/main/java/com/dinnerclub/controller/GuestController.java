package com.dinnerclub.controller;

import com.dinnerclub.entity.Guest;
import com.dinnerclub.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/guest")
public class GuestController {
    private final GuestService guestService;

    @Operation(summary = "Find guest by his unique email.")
    @GetMapping("/guest-by-email")
    public ResponseEntity<Guest> getGuestByEmail(@RequestParam String email) {
        return ResponseEntity.ok(guestService.findByEmail(email));
    }

    @Operation(summary = "Find all guests by name.")
    @GetMapping("/all-guests-by-name")
    public ResponseEntity<List<Guest>> getAllGuestsByName(@RequestParam String name) {
        return ResponseEntity.ok(guestService.findAllByName(name));
    }

    @Operation(summary = "Find all guests who didn't show up on certain event.")
    @GetMapping("/all-guests-who-didnt-show-up-on-event")
    public ResponseEntity<List<Guest>> getAllWhoDidntShowUpByEventScheduleId(@RequestParam Integer eventScheduleId) {
        return ResponseEntity.ok(guestService.findAllWhoDidntShowUpByEventScheduleId(eventScheduleId));
    }
    @Operation(summary = "Find all guests for a certain event.")
    @GetMapping("/all-by-event")
    public ResponseEntity<List<Guest>> getAllGuestsByName(@RequestParam Integer eventScheduleId) {
        return ResponseEntity.ok(guestService.findAllByEventScheduleId(eventScheduleId));
    }
}
