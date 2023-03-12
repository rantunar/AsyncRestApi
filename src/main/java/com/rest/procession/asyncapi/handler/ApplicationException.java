package com.rest.procession.asyncapi.handler;

public class ApplicationException extends RuntimeException{
  public ApplicationException(String msg, Integer replayId){
    super(msg);
  }
}
