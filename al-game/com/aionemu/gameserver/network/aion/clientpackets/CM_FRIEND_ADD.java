package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_RESPONSE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.SocialService;
import com.aionemu.gameserver.world.World;




















public class CM_FRIEND_ADD
  extends AionClientPacket
{
  private String targetName;
  
  public CM_FRIEND_ADD(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.targetName = readS();
  }







  
  protected void runImpl() {
    final Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    final Player targetPlayer = World.getInstance().findPlayer(this.targetName);

    
    if (!this.targetName.equalsIgnoreCase(activePlayer.getName()))
    {


      
      if (targetPlayer == null) {
        
        sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(this.targetName, 1));
      }
      else if (activePlayer.getFriendList().getFriend(targetPlayer.getObjectId()) != null) {
        
        sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(targetPlayer.getName(), 2));
      }
      else if (activePlayer.getFriendList().isFull()) {
        
        sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BUDDYLIST_LIST_FULL);
      }
      else if (targetPlayer.getFriendList().isFull()) {
        
        sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(targetPlayer.getName(), 5));
      }
      else if (activePlayer.getBlockList().contains(targetPlayer.getObjectId())) {
        
        sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(targetPlayer.getName(), 8));
      }
      else if (targetPlayer.getBlockList().contains(activePlayer.getObjectId())) {
        
        sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.YOU_ARE_BLOCKED_BY(this.targetName));
      }
      else {
        
        RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)activePlayer)
          {
            
            public void acceptRequest(Creature requester, Player responder)
            {
              if (!targetPlayer.getCommonData().isOnline()) {
                
                CM_FRIEND_ADD.this.sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(CM_FRIEND_ADD.this.targetName, 1));
              } else {
                if (activePlayer.getFriendList().isFull() || responder.getFriendList().isFull()) {
                  return;
                }



                
                SocialService.makeFriends((Player)requester, responder);
              } 
            }



            
            public void denyRequest(Creature requester, Player responder) {
              CM_FRIEND_ADD.this.sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(CM_FRIEND_ADD.this.targetName, 4));
            }
          };

        
        boolean requested = targetPlayer.getResponseRequester().putRequest(900841, responseHandler);
        
        if (!requested) {
          
          sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BUDDYLIST_BUSY);
        }
        else {
          
          if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.FRIEND)) {
            
            sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_FRIEND(targetPlayer.getName()));
            
            return;
          } 
          targetPlayer.getClientConnection().sendPacket((AionServerPacket)new SM_QUESTION_WINDOW(900841, activePlayer.getObjectId(), new Object[] { activePlayer.getName() }));
        } 
      } 
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_FRIEND_ADD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
