package com.rest.procession.asyncapi.restmodel;

import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplayRequest {

  @NotNull(message = "replayForm can't be null")
  private LocalDateTime replayFrom;

  //@NotNull(message = "sourceSystem can't be null")
  private SourceSystem sourceSystem;

  //@NotNull(message = "recoveryArea can't be null")
  private RecoveryArea recoveryArea;

}
