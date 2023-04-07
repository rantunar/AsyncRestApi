package com.rest.procession.asyncapi.repository;

import com.rest.procession.asyncapi.entity.EventReplay;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventReplayRepository extends JpaRepository<EventReplay, Integer> {
  Optional<Boolean> findByStatus(String status);
}
