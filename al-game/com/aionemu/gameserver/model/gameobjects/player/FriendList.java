package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_NOTIFY;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_UPDATE;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;























public class FriendList
  implements Iterable<Friend>
{
  public static final int MAX_FRIENDS = 10;
  private Status status = Status.OFFLINE;


  
  private final Queue<Friend> friends;


  
  private Player player;


  
  public FriendList(Player player) {
    this(player, new ConcurrentLinkedQueue<Friend>());
  }





  
  public FriendList(Player owner, Collection<Friend> newFriends) {
    this.friends = new ConcurrentLinkedQueue<Friend>(newFriends);
    this.player = owner;
  }






  
  public Friend getFriend(int objId) {
    for (Friend friend : this.friends) {
      
      if (friend.getOid() == objId)
        return friend; 
    } 
    return null;
  }





  
  public int getSize() {
    return this.friends.size();
  }






  
  public void addFriend(Friend friend) {
    this.friends.add(friend);
  }






  
  public Friend getFriend(String name) {
    for (Friend friend : this.friends) {
      if (friend.getName().equalsIgnoreCase(name))
        return friend; 
    }  return null;
  }








  
  public void delFriend(int friendOid) {
    Iterator<Friend> it = iterator();
    while (it.hasNext()) {
      if (((Friend)it.next()).getOid() == friendOid)
        it.remove(); 
    } 
  }
  
  public boolean isFull() {
    return (getSize() >= 10);
  }





  
  public Status getStatus() {
    return this.status;
  }






  
  public void setStatus(Status status) {
    Status previousStatus = this.status;
    this.status = status;
    
    for (Friend friend : this.friends) {
      
      if (friend.isOnline()) {
        
        Player friendPlayer = friend.getPlayer();

        
        if (friendPlayer == null) {
          continue;
        }
        friendPlayer.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_UPDATE(this.player.getObjectId()));
        
        if (previousStatus == Status.OFFLINE) {

          
          friendPlayer.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_NOTIFY(0, this.player.getName())); continue;
        } 
        if (status == Status.OFFLINE)
        {
          
          friendPlayer.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_NOTIFY(1, this.player.getName()));
        }
      } 
    } 
  }







  
  public Iterator<Friend> iterator() {
    return this.friends.iterator();
  }



  
  public enum Status
  {
    OFFLINE(0),


    
    ONLINE(1),


    
    AWAY(2);
    
    int value;

    
    Status(int value) {
      this.value = value;
    }

    
    public int getIntValue() {
      return this.value;
    }







    
    public static Status getByValue(int value) {
      for (Status stat : values()) {
        if (stat.getIntValue() == value)
          return stat; 
      }  return null;
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\FriendList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
