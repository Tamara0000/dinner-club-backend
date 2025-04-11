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
@Table(name = "guest")
public class Guest extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Integer id;
    @Column(name = "name")
    private String theme;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone")
    private String phone;

}
