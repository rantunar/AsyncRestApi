package com.rest.procession.asyncapi.handler;

public class RecordNotFoundException extends RuntimeException{
  public RecordNotFoundException(String msg){
    super(msg);
  }
}
