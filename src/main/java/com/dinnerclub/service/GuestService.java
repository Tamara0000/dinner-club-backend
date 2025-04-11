package com.dinnerclub.service;


import com.dinnerclub.entity.Guest;

import java.util.List;

public interface GuestService {
    Guest findByEmail(String email);
    List<Guest> findAllByName(String name);
    List<Guest> findAllWhoDidntShowUpByEventScheduleId(Integer eventId);
    List<Guest> findAllByEventScheduleId(Integer eventId);
}
