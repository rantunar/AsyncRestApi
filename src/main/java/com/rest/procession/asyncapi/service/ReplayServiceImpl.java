package com.rest.procession.asyncapi.service;

import com.rest.procession.asyncapi.entity.EventMessage;
import com.rest.procession.asyncapi.entity.EventReplay;
import com.rest.procession.asyncapi.handler.JobInProgressException;
import com.rest.procession.asyncapi.handler.RecordNotFoundException;
import com.rest.procession.asyncapi.repository.EventMessageRepository;
import com.rest.procession.asyncapi.repository.EventReplayRepository;
import com.rest.procession.asyncapi.restmodel.ReplayRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReplayServiceImpl implements ReplayService {

  @Autowired
  EventReplayRepository eventReplayRepository;

  @Autowired
  EventMessageRepository eventMessageRepository;

  private final static String IN_PROGRESS = "In-Progress";
  private final static String COMPLETED = "Completed";
  private final static String ERROR = "Error";

  @Override
  public EventReplay storeEventReplay(final ReplayRequest replayRequest) {
    return eventReplayRepository.save(
        EventReplay.builder()
            .status(IN_PROGRESS)
            .recoveryArea(replayRequest.getRecoveryArea().name())
            .replayAt(LocalDateTime.now())
            .build());
  }

  @Override
  public List<EventMessage> fetchEventMessage(final ReplayRequest replayRequest){
    if(eventReplayRepository.findByStatus(IN_PROGRESS).isPresent()) throw new JobInProgressException("Job is already IN-PROGRESS");
    List<EventMessage> eventMessages = eventMessageRepository.findAllByInsertedAtBetween(replayRequest.getReplayFrom(),
        LocalDateTime.now());
    if(eventMessages.isEmpty()) throw new RecordNotFoundException("Record not found in event message for replay date from = "+replayRequest.getReplayFrom());
    return eventMessages;
  }

  @Override
  @Async
  public CompletableFuture<EventReplay> submitJob(final Integer replayId,
      final ReplayRequest replayRequest, final List<EventMessage> eventMessage){
    EventReplay eventReplay = eventReplayRepository.findById(replayId).orElseThrow(() -> new RecordNotFoundException("Record not found for replay id = "+replayId));
    try {
      switch (replayRequest.getSourceSystem().name()) {
        case "CSE":
          switch (replayRequest.getRecoveryArea().name()) {
            case "CSE_DB":
              eventReplay.setStatus(COMPLETED);
              eventReplay = eventReplayRepository.save(eventReplay);
          }
      }
    } catch (final Exception e){
      eventReplay.setStatus(ERROR);
      eventReplay.setMessage(e.getMessage());
      eventReplay = eventReplayRepository.save(eventReplay);
    }
    return CompletableFuture.completedFuture(eventReplay);
  }

  @Override
  public EventReplay getReplayData(final Integer replayId){
    return eventReplayRepository.findById(replayId).orElseThrow(() -> new RecordNotFoundException("Record not found for replay id = "+replayId));
  }
}