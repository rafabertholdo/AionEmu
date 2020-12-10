package com.aionemu.gameserver.model.gameobjects.player;

import java.util.HashMap;
import org.apache.log4j.Logger;
























public class ResponseRequester
{
  private static final Logger log = Logger.getLogger(ResponseRequester.class);
  
  private Player player;
  private HashMap<Integer, RequestResponseHandler> map = new HashMap<Integer, RequestResponseHandler>();

  
  public ResponseRequester(Player player) {
    this.player = player;
  }






  
  public synchronized boolean putRequest(int messageId, RequestResponseHandler handler) {
    if (this.map.containsKey(Integer.valueOf(messageId))) {
      return false;
    }
    this.map.put(Integer.valueOf(messageId), handler);
    return true;
  }








  
  public synchronized boolean respond(int messageId, int response) {
    RequestResponseHandler handler = this.map.get(Integer.valueOf(messageId));
    if (handler != null) {
      
      this.map.remove(Integer.valueOf(messageId));
      log.debug("RequestResponseHandler triggered for response code " + messageId + " from " + this.player.getName());
      handler.handle(this.player, response);
      return true;
    } 
    return false;
  }




  
  public synchronized void denyAll() {
    for (RequestResponseHandler handler : this.map.values())
    {
      handler.handle(this.player, 0);
    }
    
    this.map.clear();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\ResponseRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
