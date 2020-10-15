package com.twoyang.prodactivity.server.business.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @PostFilter("filterObject.usr.id == authentication.principal")
    default List<TaskEntity> findAllForUser() {
        return findAll();
    }
}
