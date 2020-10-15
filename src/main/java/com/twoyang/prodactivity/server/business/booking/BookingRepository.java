package com.twoyang.prodactivity.server.business.booking;

import com.twoyang.prodactivity.server.business.tasks.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    @PostFilter("filterObject.usr.id == authentication.principal")
    default List<BookingEntity> findAllForUser() {
        return findAll();
    }

    @PostFilter("filterObject.usr.id == authentication.principal")
    List<BookingEntity> findByEndTimeGreaterThanAndTask(Long endTime, TaskEntity task);
}
