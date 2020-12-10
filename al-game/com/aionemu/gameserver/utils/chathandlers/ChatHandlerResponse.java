package com.aionemu.gameserver.utils.chathandlers;

public class ChatHandlerResponse {
  public static final ChatHandlerResponse BLOCKED_MESSAGE = new ChatHandlerResponse(true, "");

  private boolean messageBlocked;

  private String message;

  public ChatHandlerResponse(boolean messageBlocked, String message) {
    this.messageBlocked = messageBlocked;
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean isBlocked() {
    return this.messageBlocked;
  }
}
