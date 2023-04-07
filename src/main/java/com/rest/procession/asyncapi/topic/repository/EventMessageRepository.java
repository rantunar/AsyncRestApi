package com.rest.procession.asyncapi.topic.repository;

import com.rest.procession.asyncapi.topic.entity.EventMessage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMessageRepository extends JpaRepository<EventMessage, Integer> {

  List<EventMessage> findAllByInsertedAtBetween(LocalDateTime from, LocalDateTime to);
}
