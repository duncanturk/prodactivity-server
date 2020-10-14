package com.twoyang.prodactivity.server.business.booking;

import com.twoyang.prodactivity.server.business.tasks.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findByEndTimeGreaterThanAndTask(Long endTime, TaskEntity task);
}
