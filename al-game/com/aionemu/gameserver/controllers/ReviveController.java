package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
import com.aionemu.gameserver.skillengine.effect.RebirthEffect;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.List;




















public class ReviveController
{
  private Player player;
  private int rebirthResurrectPercent;
  
  public ReviveController(Player player) {
    this.player = player;
    this.rebirthResurrectPercent = 1;
  }




  
  public void skillRevive() {
    revive(10, 10);
    PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.RESURRECT), true);
    
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
    
    if (this.player.isInPrison()) {
      TeleportService.teleportToPrison(this.player);
    }
  }



  
  public void rebirthRevive() {
    if (this.rebirthResurrectPercent <= 0) {
      
      PacketSendUtility.sendMessage(this.player, "Error: Rebirth effect missing percent.");
      this.rebirthResurrectPercent = 5;
    } 
    revive(this.rebirthResurrectPercent, this.rebirthResurrectPercent);
    PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.RESURRECT), true);
    
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
    
    if (this.player.isInPrison()) {
      TeleportService.teleportToPrison(this.player);
    }
  }



  
  public void bindRevive() {
    revive(25, 25);
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);

    
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_PLAYER_INFO(this.player, false));
    
    if (this.player.isInPrison()) {
      TeleportService.teleportToPrison(this.player);
    } else {
      TeleportService.moveToBindLocation(this.player, true);
    } 
  }
  
  public void kiskRevive() {
    Kisk kisk = this.player.getKisk();
    
    revive(25, 25);
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
    
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_PLAYER_INFO(this.player, false));
    
    if (this.player.isInPrison()) {
      TeleportService.teleportToPrison(this.player);
    } else {
      
      TeleportService.moveToKiskLocation(this.player);
      kisk.resurrectionUsed(this.player);
    } 
  }

  
  private void revive(int hpPercent, int mpPercent) {
    this.player.getLifeStats().setCurrentHpPercent(hpPercent);
    this.player.getLifeStats().setCurrentMpPercent(mpPercent);
    this.player.getCommonData().setDp(0);
    this.player.getLifeStats().triggerRestoreOnRevive();
    
    this.player.getController().onRespawn();
  }




  
  public void itemSelfRevive() {
    Item item = getSelfRezStone(this.player);
    if (item == null) {

      
      PacketSendUtility.sendMessage(this.player, "Error: Couldn't find self-rez item.");
      
      return;
    } 
    
    int useDelay = item.getItemTemplate().getDelayTime();
    this.player.addItemCoolDown(item.getItemTemplate().getDelayId(), System.currentTimeMillis() + useDelay, useDelay / 1000);
    PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(this.player.getObjectId(), item.getObjectId(), item.getItemTemplate().getTemplateId()), true);
    
    ItemService.decreaseItemCount(this.player, item, 1L);

    
    revive(15, 15);
    PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.RESURRECT), true);
    
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)SM_SYSTEM_MESSAGE.REVIVE);
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
    
    if (this.player.isInPrison()) {
      TeleportService.teleportToPrison(this.player);
    }
  }






  
  private Item getSelfRezStone(Player player) {
    Item item = null;
    item = tryStone(161001001);
    if (item == null)
      item = tryStone(161000003); 
    if (item == null)
      item = tryStone(161000004); 
    if (item == null)
      item = tryStone(161000001); 
    return item;
  }





  
  private Item tryStone(int stoneId) {
    Item item = this.player.getInventory().getFirstItemByItemId(stoneId);
    if (item != null && this.player.isItemUseDisabled(item.getItemTemplate().getDelayId()))
      item = null; 
    return item;
  }






  
  public boolean checkForSelfRezItem(Player player) {
    return (getSelfRezStone(player) != null);
  }







  
  public boolean checkForSelfRezEffect(Player player) {
    List<Effect> effects = player.getEffectController().getAbnormalEffects();
    for (Effect effect : effects) {
      for (EffectTemplate template : effect.getEffectTemplates()) {
        
        if (template.getEffectid() == 160) {
          
          RebirthEffect rebirthEffect = (RebirthEffect)template;
          this.rebirthResurrectPercent = rebirthEffect.getResurrectPercent();
          return true;
        } 
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\ReviveController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
