package com.rest.procession.asyncapi.controllers;

import com.rest.procession.asyncapi.entity.EventMessage;
import com.rest.procession.asyncapi.entity.EventReplay;
import com.rest.procession.asyncapi.handler.ApplicationException;
import com.rest.procession.asyncapi.handler.RecordNotFoundException;
import com.rest.procession.asyncapi.restmodel.ErrorResponse;
import com.rest.procession.asyncapi.restmodel.ReplayRequest;
import com.rest.procession.asyncapi.service.ReplayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  @Autowired ReplayService replayService;

  @Operation(summary = "Post replay request")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "replay request successfully submitted",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = EventReplay.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Request body validation failed",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Application error",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "If job is already in-progress",
            content = @Content),
        @ApiResponse(
            responseCode = "415",
            description = "Unsupported media type",
            content = @Content)
      })
  @PostMapping(value = "/replay")
  public ResponseEntity<EventReplay> replay(@Validated @RequestBody ReplayRequest replayRequest) {
    List<EventMessage> eventMessage = replayService.fetchEventMessage(replayRequest);
    EventReplay eventReplay = replayService.storeEventReplay(replayRequest);
    replayService.submitJob(eventReplay.getId(), replayRequest, eventMessage);
    return new ResponseEntity<>(eventReplay, HttpStatus.ACCEPTED);
  }

  @Operation(description = "Get a replay data by it's id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the replay data",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = EventReplay.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Replay data not found",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Application error",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping(value = "/replay/{replay-id}")
  public ResponseEntity<EventReplay> getReplayData(
      @PathVariable(value = "replay-id") Integer replayId) {
    return new ResponseEntity<>(replayService.getReplayData(replayId), HttpStatus.OK);
  }

  @PostMapping("/replay/message")
  public ResponseEntity<?> saveEventMessage() throws IOException {
    replayService.saveEventMessage();
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
