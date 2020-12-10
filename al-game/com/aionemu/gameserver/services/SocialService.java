package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.BlockListDAO;
import com.aionemu.gameserver.dao.FriendListDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BLOCK_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BLOCK_RESPONSE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_NOTIFY;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_RESPONSE;
import com.aionemu.gameserver.world.World;
































public class SocialService
{
  public static boolean addBlockedUser(Player player, Player blockedPlayer, String reason) {
    if (((BlockListDAO)DAOManager.getDAO(BlockListDAO.class)).addBlockedUser(player.getObjectId(), blockedPlayer.getObjectId(), reason)) {
      
      player.getBlockList().add(new BlockedPlayer(blockedPlayer.getCommonData(), reason));
      
      player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(0, blockedPlayer.getName()));
      
      player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_LIST());

      
      return true;
    } 
    return false;
  }










  
  public static boolean deleteBlockedUser(Player player, int blockedUserId) {
    if (((BlockListDAO)DAOManager.getDAO(BlockListDAO.class)).delBlockedUser(player.getObjectId(), blockedUserId)) {
      
      player.getBlockList().remove(blockedUserId);
      player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(1, ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).loadPlayerCommonData(blockedUserId).getName()));




      
      player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_LIST());
      
      return true;
    } 
    return false;
  }












  
  public static boolean setBlockedReason(Player player, BlockedPlayer target, String reason) {
    if (!target.getReason().equals(reason))
    {
      if (((BlockListDAO)DAOManager.getDAO(BlockListDAO.class)).setReason(player.getObjectId(), target.getObjId(), reason)) {
        
        target.setReason(reason);
        player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_LIST());
        
        return true;
      } 
    }
    return false;
  }






  
  public static void makeFriends(Player friend1, Player friend2) {
    ((FriendListDAO)DAOManager.getDAO(FriendListDAO.class)).addFriends(friend1, friend2);
    
    friend1.getFriendList().addFriend(new Friend(friend2.getCommonData()));
    friend2.getFriendList().addFriend(new Friend(friend1.getCommonData()));
    
    friend1.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());
    
    friend2.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());

    
    friend1.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(friend2.getName(), 0));
    
    friend2.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(friend1.getName(), 0));
  }










  
  public static void deleteFriend(Player deleter, int exFriend2Id) {
    if (((FriendListDAO)DAOManager.getDAO(FriendListDAO.class)).delFriends(deleter.getObjectId(), exFriend2Id)) {

      
      Player friend2Player = PlayerService.getCachedPlayer(exFriend2Id);
      
      if (friend2Player == null) {
        friend2Player = World.getInstance().findPlayer(exFriend2Id);
      }
      String friend2Name = (friend2Player != null) ? friend2Player.getName() : ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).loadPlayerCommonData(exFriend2Id).getName();


      
      deleter.getFriendList().delFriend(exFriend2Id);
      
      deleter.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());
      
      deleter.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(friend2Name, 6));




      
      if (friend2Player != null) {
        
        friend2Player.getFriendList().delFriend(deleter.getObjectId());
        
        if (friend2Player.isOnline()) {
          
          friend2Player.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_NOTIFY(2, deleter.getName()));
          
          friend2Player.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());
        } 
      } 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\SocialService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
