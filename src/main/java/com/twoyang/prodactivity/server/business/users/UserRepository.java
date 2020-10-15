package com.twoyang.prodactivity.server.business.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);
}
