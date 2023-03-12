package com.rest.procession.asyncapi.service;

import com.rest.procession.asyncapi.entity.EventMessage;
import com.rest.procession.asyncapi.entity.EventReplay;
import com.rest.procession.asyncapi.handler.ApplicationException;
import com.rest.procession.asyncapi.handler.RecordNotFoundException;
import com.rest.procession.asyncapi.restmodel.ReplayRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;

public interface ReplayService {

  public EventReplay storeEventReplay(ReplayRequest replayRequest);

  public List<EventMessage> fetchEventMessage(ReplayRequest replayRequest) throws RecordNotFoundException;

  @Async
  CompletableFuture<EventReplay> submitJob(Integer replayId, ReplayRequest replayRequest,
      List<EventMessage> eventMessage);

  EventReplay getReplayData(Integer replayId);
}
