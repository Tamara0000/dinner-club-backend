package com.dinnerclub.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "event_attendance")
public class EventAttendance extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_attendance_id")
    private Integer id;
    @JoinColumn(name = "event_schedule_fk", referencedColumnName = "event_schedule_id")
    @ManyToOne
    private EventSchedule eventSchedule;
    @JoinColumn(name = "guest_fk", referencedColumnName = "guest_id")
    @ManyToOne
    private Guest guest;
    @Column(name = "confirmed")
    private Boolean confirmed;
    @Column(name = "showed_up")
    private Boolean showedUp;
}
