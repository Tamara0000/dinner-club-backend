package com.dinnerclub.repository;

import com.dinnerclub.entity.EventSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface EventScheduleRepository extends JpaRepository<EventSchedule, Integer>, JpaSpecificationExecutor<EventSchedule> {
    //examples of overloading
    List<EventSchedule> findAllByDateGreaterThanEqual(LocalDate now);
    Page<EventSchedule> findAllByDateGreaterThanEqual(LocalDate now, Pageable page);
    List<EventSchedule> findAllByCancelledIsTrue();
    Page<EventSchedule> findAllByCancelledIsTrue(Pageable pageable);
    List<EventSchedule> findAllByCompletedIsTrue();
    Page<EventSchedule> findAllByCompletedIsTrue(Pageable pageable);
    List<EventSchedule> findAllByEvent_Id(Integer eventId);
}