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
@Table(name = "event")
public class Event extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer id;
    @Column(name = "theme", unique = true)
    private String theme;
    @Column(name = "location")
    private Integer location;
}
