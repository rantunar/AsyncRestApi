package com.rest.procession.asyncapi.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
  @Column
  private byte[] payload;
  @Column(name = "inserted_at")
  private LocalDateTime insertedAt;

}
