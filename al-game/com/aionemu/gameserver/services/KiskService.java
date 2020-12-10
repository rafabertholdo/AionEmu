package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.container.KiskContainer;




















public class KiskService
{
  private static final KiskContainer kiskContainer = new KiskContainer();






  
  public Kisk getKisk(Player player) {
    return kiskContainer.get(player);
  }





  
  public static void removeKisk(Kisk kisk) {
    for (Player member : kisk.getCurrentMemberList()) {
      
      kiskContainer.remove(member);
      member.setKisk(null);
      TeleportService.sendSetBindPoint(member);
      if (member.getLifeStats().isAlreadyDead()) {
        PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_DIE(false, false, 0));
      }
    } 
  }






  
  public static void onBind(Kisk kisk, Player player) {
    if (player.getKisk() != null) {
      
      kiskContainer.remove(player);
      player.getKisk().removePlayer(player);
    } 
    
    kiskContainer.add(kisk, player);
    kisk.addPlayer(player);

    
    TeleportService.sendSetBindPoint(player);

    
    PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_BINDSTONE_REGISTER);

    
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_LEVEL_UPDATE(player.getObjectId(), 2, player.getCommonData().getLevel()), true);
  }





  
  public static void onLogin(Player player) {
    Kisk kisk = kiskContainer.get(player);
    if (kisk != null)
      kisk.reAddPlayer(player); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\KiskService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
