package com.dinnerclub.scheduler;

import com.dinnerclub.entity.EventAttendance;
import com.dinnerclub.mail.MailService;
import com.dinnerclub.repository.EventAttendanceRepository;
import com.dinnerclub.repository.EventScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class GuestReminderForEventScheduler {
    private final MailService mailService;
    private final EventScheduleRepository eventScheduleRepository;
    private final EventAttendanceRepository eventAttendanceRepository;
    @Scheduled(cron = "${event.reminder.scheduler}")
    public void guestReminderForEvent(){
        try {
            //uncomment if you really want the mail to be sent since it's implemented
//            reminderForUpcomingEvent();
        } catch (Exception e) {
            log.error("Error occurred while sending reminders for events: ", e);
        }
    }

    private void reminderForUpcomingEvent(){
        LocalDate now = LocalDate.now();
        String subject = "Reminder for tomorrow's event and it's secret location revealed";

        //send reminder mail if date of the event is tomorrow from today
        eventScheduleRepository.findAllByDateGreaterThanEqual(now)
                .stream()
                .filter(eventSchedule -> eventSchedule.getDate() == now.plusDays(1))
                .flatMap(eventSchedule -> eventAttendanceRepository.findAllByConfirmedIsTrueAndEventSchedule_Id(eventSchedule.getId())
                        .stream())
                .forEach(eventAttendance ->
                        mailService.sendMailToGuest(eventAttendance.getGuest().getEmail(), subject, getMailContent(eventAttendance)));
    }

    private String getMailContent(EventAttendance eventAttendance){
        return "Dear guest, <br><br>"
                + "Just a reminder that the event in theme " + eventAttendance.getEventSchedule().getEvent().getTheme()
                + " is tomorrow! The event location is: " + eventAttendance.getEventSchedule().getEvent().getLocation();
    }
}
