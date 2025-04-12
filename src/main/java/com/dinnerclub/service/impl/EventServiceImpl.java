package com.dinnerclub.service.impl;

import com.dinnerclub.entity.Event;
import com.dinnerclub.entity.EventAttendance;
import com.dinnerclub.entity.EventSchedule;
import com.dinnerclub.mail.MailService;
import com.dinnerclub.repository.EventAttendanceRepository;
import com.dinnerclub.repository.EventRepository;
import com.dinnerclub.repository.EventScheduleRepository;
import com.dinnerclub.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final MailService mailService;
    private final EventRepository eventRepository;
    private final EventScheduleRepository eventScheduleRepository;
    private final EventAttendanceRepository eventAttendanceRepository;

    @Override
    public Event update(Event event) {
        //find what has been changed(updated)
        Optional<Event> eventFromDb = eventRepository.findById(event.getId());
        boolean changeOfTheme = false;
        boolean changeOfLocation = false;

        if (eventFromDb.isPresent()) {
            if (!eventFromDb.get().getLocation().equals(event.getLocation())) {
                changeOfLocation = true;
            }
            if (!eventFromDb.get().getTheme().equals(event.getTheme())) {
                changeOfTheme = true;
            }
        }

        //update
        Event updatedEvent = eventRepository.save(event);

        //get all the schedules from that event and notify the guests attending the event about the change
        List<Integer> eventScheduleIds = eventScheduleRepository.findAllByEvent_Id(updatedEvent.getId())
                .stream().map(EventSchedule::getId).collect(Collectors.toList());

        String subject = "Change od event details";
        boolean finalChangeOfLocation = changeOfLocation;
        boolean finalChangeOfTheme = changeOfTheme;

        //uncomment if you really want the mail to be sent since it's implemented
//        eventAttendanceRepository.findAllByConfirmedIsTrueAndEventSchedule_IdIn(eventScheduleIds)
//                .stream()
//                .forEach(eventAttendance -> {
//                    mailService.sendMailToGuest(eventAttendance.getGuest().getEmail(), subject, getMailContent(finalChangeOfLocation, finalChangeOfTheme, eventAttendance));
//                });
        return updatedEvent;
    }

    private String getMailContent(boolean changeOfLocation, boolean changeOfTheme, EventAttendance eventAttendance) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateOfEvent = formatter.format(eventAttendance.getEventSchedule().getDate());
        String theme = eventAttendance.getEventSchedule().getEvent().getTheme();
        String location = eventAttendance.getEventSchedule().getEvent().getLocation();

        StringBuilder mailContent = new StringBuilder("Dear guest, <br><br>");
        mailContent.append("The event that is taking place on ")
                .append(dateOfEvent)
                .append(" has been modified. ");

        if (changeOfLocation && changeOfTheme) {
            mailContent.append("new location is: ");
            mailContent.append(location);
            mailContent.append(" and new theme is: ");
            mailContent.append(theme);
        } else if (changeOfLocation) {
            mailContent.append("new location is: ");
            mailContent.append(location);
        } else {
            mailContent.append("new theme is: ");
            mailContent.append(theme);
        }
        return mailContent.toString();
    }
}
