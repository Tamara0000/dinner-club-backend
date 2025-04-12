package com.dinnerclub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "event_schedule")
public class EventSchedule extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_schedule_id")
    private Integer id;
    //EventSchedule can have many Events because if its cancelled, it can be rescheduled for another date
    @JoinColumn(name = "event_fk", referencedColumnName = "event_id")
    @ManyToOne
    private Event event;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "completed")
    private Boolean completed;
    @Column(name = "cancelled")
    private Boolean cancelled;
}
