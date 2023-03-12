package com.rest.procession.asyncapi.repository;

import com.rest.procession.asyncapi.entity.EventMessage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventMessageRepository extends JpaRepository<EventMessage, Integer> {

  List<EventMessage> findAllByInsertedAtBetween(LocalDateTime from, LocalDateTime to);
}
