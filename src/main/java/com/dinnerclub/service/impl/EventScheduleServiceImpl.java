package com.dinnerclub.service.impl;

import com.dinnerclub.entity.EventAttendance;
import com.dinnerclub.entity.EventSchedule;
import com.dinnerclub.mail.MailService;
import com.dinnerclub.repository.EventAttendanceRepository;
import com.dinnerclub.repository.EventScheduleRepository;
import com.dinnerclub.service.EventScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventScheduleServiceImpl implements EventScheduleService {
    private final EventScheduleRepository eventScheduleRepository;
    private final EventAttendanceRepository eventAttendanceRepository;
    private final MailService mailService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @Override
    public List<EventSchedule> findAll() {
        return eventScheduleRepository.findAll();
    }

    //the idea is to separate to three different tabs(and therefore services) on frontend, so it will be faster and more readable
    @Override
    public List<EventSchedule> findAllUpcomingEvents() {
        LocalDate now = LocalDate.now();
        return eventScheduleRepository.findAllByDateGreaterThanEqual(now);
    }

    @Override
    public List<EventSchedule> findAllCancelledEvents() {
        return eventScheduleRepository.findAllByCancelledIsTrue();
    }

    @Override
    public List<EventSchedule> findAllCompletedEvents() {
        return eventScheduleRepository.findAllByCompletedIsTrue();
    }

    @Override
    public Page<EventSchedule> findAllUpcomingEvents(Pageable page) {
        LocalDate now = LocalDate.now();
        return eventScheduleRepository.findAllByDateGreaterThanEqual(now, page);
    }

    @Override
    public Page<EventSchedule> findAllCancelledEvents(Pageable page) {
        return eventScheduleRepository.findAllByCancelledIsTrue(page);
    }

    @Override
    public Page<EventSchedule> findAllCompletedEvents(Pageable page) {
        return eventScheduleRepository.findAllByCompletedIsTrue(page);
    }

    //on the frontend it is enabled to change only date or cancelled
    @Override
    public EventSchedule update(EventSchedule eventSchedule) {
        //find what has been changed(updated)
        Optional<EventSchedule> eventScheduleFromDb = eventScheduleRepository.findById(eventSchedule.getId());
        boolean changeOfDate = false;
        boolean changeOfCancellation = false;
        String beforeDate = "";

        if (eventScheduleFromDb.isPresent()) {
            if (!eventScheduleFromDb.get().getDate().equals(eventSchedule.getDate())) {
                changeOfDate = true;
                beforeDate = formatter.format(eventScheduleFromDb.get().getDate());
            }
            if (!eventScheduleFromDb.get().getCancelled().equals(eventSchedule.getCancelled())) {
                changeOfCancellation = true;
            }
        }

        //update
        EventSchedule updatedEventSchedule = eventScheduleRepository.save(eventSchedule);

        //get all guests who are attending the event and notify them about the change
        String subject = "Change od event schedule";
        boolean finalChangeOfDate = changeOfDate;
        boolean finalChangeOfCancellation = changeOfCancellation;
        String finalBeforeDate = beforeDate;
        eventAttendanceRepository.findAllByConfirmedIsTrueAndEventSchedule_Id(updatedEventSchedule.getId())
                .stream()
                .forEach(eventAttendance -> {
                    mailService.sendMailToGuest(eventAttendance.getGuest().getEmail(), subject, getMailContent(finalChangeOfDate, finalBeforeDate, finalChangeOfCancellation, eventAttendance));
                });

        return updatedEventSchedule;
    }

    private String getMailContent(boolean changeOfDate, String beforeDate, boolean changeOfCancellation, EventAttendance eventAttendance) {
        String mailContent;
        String cancelled = changeOfCancellation? " is cancelled" : " isn't cancelled";

        if(changeOfDate && changeOfCancellation){
            mailContent = "Dear guest, <br><br>"
                    + "There has been a change of an event you are attending in theme " + eventAttendance.getEventSchedule().getEvent().getTheme()
                    + " and on location " + eventAttendance.getEventSchedule().getEvent().getLocation()
                    + " from date " + beforeDate + " to " + formatter.format(eventAttendance.getEventSchedule().getDate())
                    + " and the event " + cancelled;
        }else if(changeOfDate){
            mailContent = "Dear guest, <br><br>"
                    + "There has been a change of an event you are attending in theme " + eventAttendance.getEventSchedule().getEvent().getTheme()
                    + " and on location " + eventAttendance.getEventSchedule().getEvent().getLocation()
                    + " from date " + beforeDate + " to " + formatter.format(eventAttendance.getEventSchedule().getDate());
        }else{
            mailContent = "Dear guest, <br><br>"
                    + "The event you are attending in theme " + eventAttendance.getEventSchedule().getEvent().getTheme()
                    + " and on location " + eventAttendance.getEventSchedule().getEvent().getLocation()
                    + " taking place on " + formatter.format(eventAttendance.getEventSchedule().getDate())
                    + cancelled;
        }
        return mailContent;
    }

}
