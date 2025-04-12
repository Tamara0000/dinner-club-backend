package com.dinnerclub.mail.util;

import com.dinnerclub.entity.EventAttendance;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class MailUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getMailContent(boolean changeOfDate, String beforeDate, boolean changeOfCancellation, EventAttendance eventAttendance) {
        String dateOfEvent = formatter.format(eventAttendance.getEventSchedule().getDate());
        String theme = eventAttendance.getEventSchedule().getEvent().getTheme();
        String location = eventAttendance.getEventSchedule().getEvent().getLocation();
        String cancelled = changeOfCancellation? " is cancelled" : " isn't cancelled";

        StringBuilder mailContent = new StringBuilder("Dear guest, <br><br>");
        mailContent.append("There has been a change of an event you are attending in theme ")
                .append(theme)
                .append(" and on location ")
                .append(location);

        if(changeOfDate && changeOfCancellation){
            mailContent.append("from date ")
                    .append(beforeDate)
                    .append(" to ")
                    .append(dateOfEvent)
                    .append(" .And the event ")
                    .append(cancelled);
        }else if(changeOfDate){
            mailContent.append("from date ")
                    .append(beforeDate)
                    .append(" to ")
                    .append(dateOfEvent);
        }else{
            mailContent.append(" taking place on ")
                    .append(dateOfEvent)
                    .append(cancelled);
        }
        return mailContent.toString();
    }

    public static String getMailContent(boolean changeOfLocation, boolean changeOfTheme, EventAttendance eventAttendance) {
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
