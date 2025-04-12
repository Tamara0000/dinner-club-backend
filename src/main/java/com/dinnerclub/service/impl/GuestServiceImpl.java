package com.dinnerclub.service.impl;

import com.dinnerclub.entity.EventAttendance;
import com.dinnerclub.entity.Guest;
import com.dinnerclub.repository.EventAttendanceRepository;
import com.dinnerclub.repository.GuestRepository;
import com.dinnerclub.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;
    private final EventAttendanceRepository eventAttendanceRepository;

    @Override
    public Guest findByEmail(String email) {
        return guestRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("Guest with email: " + email + " not found."));
    }

    //if the list is empty, implement logic on frontend to display message about no guests with that name
    @Override
    public List<Guest> findAllByName(String name) {
        return guestRepository.findAllByNameLike(name);
    }

    @Override
    public List<Guest> findAllWhoDidntShowUpByEventScheduleId(Integer eventScheduleId) {
        //if there are a lot of records, make JPQL method in repository to make it faster because map() in stream() is making it slower
        return eventAttendanceRepository.findAllByEventSchedule_IdAndShowedUpIsFalse(eventScheduleId)
                .stream().map(EventAttendance::getGuest).collect(Collectors.toList());
    }

    @Override
    public List<Guest> findAllByEventScheduleId(Integer eventScheduleId) {
        return eventAttendanceRepository.findAllByEventSchedule_Id(eventScheduleId);
    }
}
