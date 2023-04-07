package com.rest.procession.asyncapi.entity;

import com.rest.procession.asyncapi.restmodel.RecoveryArea;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_replay")
public class EventReplay {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "status")
  private String status;

  @Column(name = "message")
  private String message;

  @Column(name = "replay_at")
  private LocalDateTime replayAt;

  @Column(name = "recovery_area")
  private String recoveryArea;
}
