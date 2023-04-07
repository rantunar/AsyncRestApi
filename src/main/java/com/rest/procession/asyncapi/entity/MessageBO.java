package com.rest.procession.asyncapi.entity;

import java.io.Serializable;
import lombok.Builder;

@Builder
public class MessageBO implements Serializable {

  private String correlationId;
  private String eventState;
  private String serviceName;

  @Override
  public String toString() {
    return "MessageBO{"
        + "correlationId='"
        + correlationId
        + '\''
        + ", eventState='"
        + eventState
        + '\''
        + ", serviceName='"
        + serviceName
        + '\''
        + '}';
  }
}
