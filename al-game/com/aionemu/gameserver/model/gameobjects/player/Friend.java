package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.PlayerClass;
























public class Friend
{
  private final PlayerCommonData pcd;
  
  public Friend(PlayerCommonData pcd) {
    this.pcd = pcd;
  }





  
  public FriendList.Status getStatus() {
    if (this.pcd.getPlayer() == null || !this.pcd.isOnline())
    {
      return FriendList.Status.OFFLINE;
    }
    return this.pcd.getPlayer().getFriendList().getStatus();
  }




  
  public String getName() {
    return this.pcd.getName();
  }

  
  public int getLevel() {
    return this.pcd.getLevel();
  }

  
  public String getNote() {
    return this.pcd.getNote();
  }

  
  public PlayerClass getPlayerClass() {
    return this.pcd.getPlayerClass();
  }

  
  public int getMapId() {
    return this.pcd.getPosition().getMapId();
  }






  
  public int getLastOnlineTime() {
    if (this.pcd.getLastOnline() == null || isOnline()) {
      return 0;
    }
    return (int)(this.pcd.getLastOnline().getTime() / 1000L);
  }

  
  public int getOid() {
    return this.pcd.getPlayerObjId();
  }

  
  public Player getPlayer() {
    return this.pcd.getPlayer();
  }

  
  public boolean isOnline() {
    return this.pcd.isOnline();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Friend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
