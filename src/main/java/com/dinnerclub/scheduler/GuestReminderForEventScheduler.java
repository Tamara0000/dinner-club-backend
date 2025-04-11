package com.dinnerclub.scheduler;

import com.dinnerclub.mail.MailService;
import com.dinnerclub.repository.EventAttendanceRepository;
import com.dinnerclub.repository.EventScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class GuestReminderForEventScheduler {
    private final MailService mailService;
    private final EventScheduleRepository eventScheduleRepository;
    private final EventAttendanceRepository eventAttendanceRepository;
    @Scheduled(cron = "${event.reminder.scheduler}")
    public void guestReminderForEvent() {
        reminderForUpcomingEvent();
    }

    private void reminderForUpcomingEvent(){
        LocalDate now = LocalDate.now();
        String subject = "Reminder for tomorrow's event";
        String mailContent = "Dear guest, <br><br>"
                + "Just a reminder that you have and event tomorrow! ";

        //send reminder mail if date of the event is tomorrow from today
        eventScheduleRepository.findAllByDateGreaterThanEqual(now)
                .stream()
                .filter(eventSchedule -> eventSchedule.getDate().getDayOfMonth() == now.getDayOfMonth() + 1)
                .flatMap(eventSchedule -> eventAttendanceRepository.findAllByConfirmedIsTrueAndEventSchedule_Id(eventSchedule.getId())
                        .stream())
                .forEach(eventAttendance ->
                        mailService.sendMailToGuest(eventAttendance.getGuest().getEmail(), subject, mailContent));
    }
}
