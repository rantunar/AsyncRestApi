package com.rest.procession.asyncapi.controllers;

import com.rest.procession.asyncapi.entity.EventMessage;
import com.rest.procession.asyncapi.entity.EventReplay;
import com.rest.procession.asyncapi.handler.ApplicationException;
import com.rest.procession.asyncapi.handler.RecordNotFoundException;
import com.rest.procession.asyncapi.restmodel.ReplayRequest;
import com.rest.procession.asyncapi.service.ReplayService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Slf4j
public class AsyncApiController {

  @Autowired
  ReplayService replayService;

  @PostMapping(value = "/replay")
  public ResponseEntity<EventReplay> replay(@Validated @RequestBody ReplayRequest replayRequest){
    List<EventMessage> eventMessage = replayService.fetchEventMessage(replayRequest);
    EventReplay eventReplay = replayService.storeEventReplay(replayRequest);
    replayService.submitJob(eventReplay.getId(),replayRequest,eventMessage);
    return new ResponseEntity<>(eventReplay, HttpStatus.ACCEPTED);
  }

  @GetMapping(value = "/replay/{replay-id}")
  public ResponseEntity<EventReplay> getReplayData(@PathVariable(value = "replay-id") Integer replayId){
    return new ResponseEntity<>(replayService.getReplayData(replayId), HttpStatus.OK);
  }

  @PostMapping("/replay/message")
  public  ResponseEntity<?> saveEventMessage() throws IOException {
    replayService.saveEventMessage();
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
