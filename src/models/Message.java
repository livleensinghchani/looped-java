package models;

import java.time.LocalDateTime;

public class Message {
  private String sender;
  private LocalDateTime timeStamp;
  private String messageContent;

  public Message(String username, String message) {
    this.sender = username;
    this.timeStamp = LocalDateTime.now();
    this. messageContent = message;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public String getSender() {
    return sender;
  }
}
