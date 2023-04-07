package com.rest.procession.asyncapi.topic.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_message")
public class EventMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "message_type")
  private String messageType;

  @Column(name = "correlation_id")
  private String correlationId;

  @Column(name = "payload", columnDefinition = "CLOB NOT NULL")
  @Lob
  private String payload;

  @Column(name = "inserted_at")
  private LocalDateTime insertedAt;
}
