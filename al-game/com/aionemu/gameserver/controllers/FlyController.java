package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class FlyController
{
  private Player player;
  
  public FlyController(Player player) {
    this.player = player;
  }

  
  public void onStopGliding() {
    if (this.player.isInState(CreatureState.GLIDING)) {
      
      this.player.unsetState(CreatureState.GLIDING);
      
      if (this.player.isInState(CreatureState.FLYING)) {
        
        this.player.setFlyState(1);
      }
      else {
        
        this.player.setFlyState(0);
        this.player.getLifeStats().triggerFpRestore();
      } 
      
      PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
    } 
  }








  
  public void endFly() {
    if (this.player.isInState(CreatureState.FLYING) || this.player.isInState(CreatureState.GLIDING)) {
      
      PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.LAND, 0, 0), true);
      this.player.unsetState(CreatureState.FLYING);
      this.player.unsetState(CreatureState.GLIDING);
      this.player.setFlyState(0);

      
      PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.START_EMOTE2, 0, 0), true);
      PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
      
      this.player.getLifeStats().triggerFpRestore();
    } 
  }





  
  public void startFly() {
    this.player.setState(CreatureState.FLYING);
    this.player.setFlyState(1);
    this.player.getLifeStats().triggerFpReduce();
    PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.START_EMOTE2, 0, 0), true);
    PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
  }











  
  public void switchToGliding() {
    if (!this.player.isInState(CreatureState.GLIDING)) {
      
      this.player.setState(CreatureState.GLIDING);
      if (this.player.getFlyState() == 0)
        this.player.getLifeStats().triggerFpReduce(); 
      this.player.setFlyState(2);
      
      PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\FlyController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
