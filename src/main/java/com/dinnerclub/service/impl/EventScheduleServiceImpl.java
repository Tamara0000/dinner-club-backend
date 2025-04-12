package com.dinnerclub.service.impl;

import com.dinnerclub.entity.EventAttendance;
import com.dinnerclub.entity.EventSchedule;
import com.dinnerclub.mail.MailService;
import com.dinnerclub.mail.util.MailUtil;
import com.dinnerclub.repository.EventAttendanceRepository;
import com.dinnerclub.repository.EventScheduleRepository;
import com.dinnerclub.service.EventScheduleService;
import lombok.RequiredArgsConstructor;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final MailUtil mailUtil;
    private final MailService mailService;
    private final EventScheduleRepository eventScheduleRepository;
    private final EventAttendanceRepository eventAttendanceRepository;


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

        //uncomment if you really want the mail to be sent
//        eventAttendanceRepository.findAllByConfirmedIsTrueAndEventSchedule_Id(updatedEventSchedule.getId())
//                .stream()
//                .forEach(eventAttendance -> {
//                    mailService.sendMailToGuest(eventAttendance.getGuest().getEmail(), subject, MailUtil.getMailContent(finalChangeOfDate, finalBeforeDate, finalChangeOfCancellation, eventAttendance));
//                });

        return updatedEventSchedule;
    }

}
