package com.rest.procession.asyncapi.handler;

public class JobInProgressException extends RuntimeException{
  public JobInProgressException(String msg){
    super(msg);
  }
}
