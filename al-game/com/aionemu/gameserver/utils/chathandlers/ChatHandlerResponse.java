package com.aionemu.gameserver.utils.chathandlers;






























public class ChatHandlerResponse
{
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\chathandlers\ChatHandlerResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
