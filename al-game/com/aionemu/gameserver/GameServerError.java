package com.aionemu.gameserver;

























public class GameServerError
  extends Error
{
  private static final long serialVersionUID = -7445873741878754767L;
  
  public GameServerError() {}
  
  public GameServerError(Throwable cause) {
    super(cause);
  }









  
  public GameServerError(String message) {
    super(message);
  }















  
  public GameServerError(String message, Throwable cause) {
    super(message, cause);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\GameServerError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
