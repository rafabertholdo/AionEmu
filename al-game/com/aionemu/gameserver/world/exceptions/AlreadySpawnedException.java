package com.aionemu.gameserver.world.exceptions;




































public class AlreadySpawnedException
  extends RuntimeException
{
  public AlreadySpawnedException() {}
  
  public AlreadySpawnedException(String s) {
    super(s);
  }









  
  public AlreadySpawnedException(String message, Throwable cause) {
    super(message, cause);
  }







  
  public AlreadySpawnedException(Throwable cause) {
    super(cause);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\exceptions\AlreadySpawnedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
