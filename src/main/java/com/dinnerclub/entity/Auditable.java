package com.dinnerclub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class Auditable {
    @Column(name = "last_modified_by", columnDefinition = "default 'system'", nullable = false, updatable = false)
    @LastModifiedBy
    @JsonIgnore
    private String lastModifiedBy = "system";
    @CreatedDate
    @JsonIgnore
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    @LastModifiedDate
    @JsonIgnore
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    @Column(name = "record_status")
    @JsonIgnore
    private Integer recordStatus = 1;
}
