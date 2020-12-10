package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.commons.objects.filter.ObjectFilter;
import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.alliance.PlayerAllianceGroup;
import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.ChatHandler;
import com.aionemu.gameserver.utils.chathandlers.ChatHandlerResponse;
import com.aionemu.gameserver.utils.chathandlers.ChatHandlers;
import javolution.util.FastList;
import org.apache.log4j.Logger;





















public class CM_CHAT_MESSAGE_PUBLIC
  extends AionClientPacket
{
  private static final Logger log = Logger.getLogger(CM_CHAT_MESSAGE_PUBLIC.class);




  
  private ChatType type;




  
  private String message;




  
  public CM_CHAT_MESSAGE_PUBLIC(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.type = ChatType.getChatTypeByInt(readC());
    this.message = readS();
  }






  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    if (player == null) {
      return;
    }
    FastList<ChatHandler> chatHandlers = ChatHandlers.getInstance().getHandlers();
    for (FastList.Node<ChatHandler> n = chatHandlers.head(), end = chatHandlers.tail(); (n = n.getNext()) != end; ) {
      
      ChatHandlerResponse response = ((ChatHandler)n.getValue()).handleChatMessage(this.type, this.message, player);
      if (response.isBlocked()) {
        return;
      }
      this.message = response.getMessage();
    } 
    
    if (RestrictionsManager.canChat(player)) {
      
      switch (this.type) {
        
        case GROUP:
          if (player.getPlayerGroup() == null && player.getPlayerAlliance() == null) {
            return;
          }
          broadcastToGroupMembers(player);
          return;
        case ALLIANCE:
          if (player.getPlayerAlliance() == null) {
            return;
          }
          if (OptionsConfig.LOG_CHAT) {
            log.info(String.format("[MESSAGE] - A <%d>: [%s]> %s", new Object[] { Integer.valueOf(player.getPlayerAlliance().getObjectId()), player.getName(), this.message }));
          }
          broadcastToAllianceMembers(player);
          return;
        
        case GROUP_LEADER:
          if (!player.isInGroup() && !player.isInAlliance()) {
            return;
          }
          if (OptionsConfig.LOG_CHAT) {
            log.info(String.format("[MESSAGE] - LA: [%s]> %s", new Object[] { player.getName(), this.message }));
          }
          
          if (player.isInGroup()) {
            broadcastToGroupMembers(player);
          } else {
            broadcastToAllianceMembers(player);
          }  return;
        case LEGION:
          if (OptionsConfig.LOG_CHAT) {
            log.info(String.format("[MESSAGE] - L <%s>: [%s]> %s", new Object[] { player.getLegion().getLegionName(), player.getName(), this.message }));
          }
          broadcastToLegionMembers(player);
          return;
      } 
      if (OptionsConfig.LOG_CHAT) {
        log.info(String.format("[MESSAGE] - ALL: [%s]> %s", new Object[] { player.getName(), this.message }));
      }
      broadcastToNonBlockedPlayers(player);
    } 
  }








  
  private void broadcastToNonBlockedPlayers(final Player player) {
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_MESSAGE(player, this.message, this.type), true, new ObjectFilter<Player>()
        {

          
          public boolean acceptObject(Player object)
          {
            return !object.getBlockList().contains(player.getObjectId());
          }
        });
  }







  
  private void broadcastToGroupMembers(Player player) {
    if (player.isInGroup()) {
      
      if (OptionsConfig.LOG_CHAT)
        log.info(String.format("[MESSAGE] - G <%d>: [%s]> %s", new Object[] { Integer.valueOf(player.getPlayerGroup().getGroupId()), player.getName(), this.message })); 
      for (Player groupPlayer : player.getPlayerGroup().getMembers())
      {
        PacketSendUtility.sendPacket(groupPlayer, (AionServerPacket)new SM_MESSAGE(player, this.message, this.type));
      }
    }
    else if (player.isInAlliance()) {
      
      if (OptionsConfig.LOG_CHAT)
        log.info(String.format("[MESSAGE] - A <%d>: [%s]> %s", new Object[] { Integer.valueOf(player.getPlayerAlliance().getObjectId()), player.getName(), this.message })); 
      PlayerAllianceGroup allianceGroup = player.getPlayerAlliance().getPlayerAllianceGroupForMember(player.getObjectId());
      if (allianceGroup != null)
      {
        for (PlayerAllianceMember allianceMember : allianceGroup.getMembers())
        {
          if (allianceMember.isOnline()) {
            PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket)new SM_MESSAGE(player, this.message, this.type));
          }
        }
      
      }
    } else {
      
      PacketSendUtility.sendMessage(player, "You are not in an alliance or group. (Error 105)");
    } 
  }






  
  private void broadcastToAllianceMembers(Player player) {
    PlayerAlliance alliance = player.getPlayerAlliance();
    if (alliance != null)
    {
      for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
        
        if (!allianceMember.isOnline())
          continue;  PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket)new SM_MESSAGE(player, this.message, this.type));
      } 
    }
  }






  
  private void broadcastToLegionMembers(Player player) {
    if (player.isLegionMember())
    {
      PacketSendUtility.broadcastPacketToLegion(player.getLegion(), (AionServerPacket)new SM_MESSAGE(player, this.message, this.type));
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CHAT_MESSAGE_PUBLIC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
