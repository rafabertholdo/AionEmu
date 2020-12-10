package com.aionemu.gameserver.model.gameobjects.player;

import java.util.Collection;
import java.util.Map;
import javolution.util.FastMap;


































public class BlockList
{
  public static final int MAX_BLOCKS = 10;
  private final Map<Integer, BlockedPlayer> blockedList;
  
  public BlockList() {
    this((Map<Integer, BlockedPlayer>)new FastMap());
  }







  
  public BlockList(Map<Integer, BlockedPlayer> initialList) {
    this.blockedList = (Map<Integer, BlockedPlayer>)new FastMap(initialList);
  }












  
  public void add(BlockedPlayer plr) {
    this.blockedList.put(Integer.valueOf(plr.getObjId()), plr);
  }









  
  public void remove(int objIdOfPlayer) {
    this.blockedList.remove(Integer.valueOf(objIdOfPlayer));
  }







  
  public BlockedPlayer getBlockedPlayer(String name) {
    for (BlockedPlayer entry : getBlockedList()) {
      if (entry.getName().equalsIgnoreCase(name))
        return entry; 
    } 
    return null;
  }

  
  public BlockedPlayer getBlockedPlayer(int playerObjId) {
    return this.blockedList.get(Integer.valueOf(playerObjId));
  }

  
  public boolean contains(int playerObjectId) {
    return this.blockedList.containsKey(Integer.valueOf(playerObjectId));
  }






  
  public int getSize() {
    return this.blockedList.size();
  }

  
  public boolean isFull() {
    return (getSize() >= 10);
  }

  
  public Collection<BlockedPlayer> getBlockedList() {
    return this.blockedList.values();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\BlockList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
