package com.twoyang.prodactivity.server.business.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @PostFilter("filterObject.usr.id == authentication.principal")
    default List<CategoryEntity> findAllForUser() {
        return findAll();
    }
}
