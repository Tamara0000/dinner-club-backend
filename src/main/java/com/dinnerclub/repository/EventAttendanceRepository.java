package com.dinnerclub.repository;

import com.dinnerclub.entity.EventAttendance;
import com.dinnerclub.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Integer>, JpaSpecificationExecutor<EventAttendance> {
    List<EventAttendance> findAllByConfirmedIsTrueAndEventSchedule_IdIn(List<Integer> eventScheduleIds);
    List<EventAttendance> findAllByConfirmedIsTrueAndEventSchedule_Id(Integer eventScheduleId);
    List<EventAttendance> findAllByEventSchedule_IdAndShowedUpIsFalse(Integer eventScheduleId);

    //method above can also be in JPQL(or NativeQuery) as this one if needed for optimization
    @Query(value = "select ea.guest " +
            "from EventAttendance ea " +
            "join ea.eventSchedule es " +
            "where es.id = :eventScheduleId")
    List<Guest> findAllByEventSchedule_Id(@Param("eventScheduleId") Integer eventScheduleId);
}
