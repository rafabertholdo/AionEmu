package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.DropService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class ActionitemController extends NpcController {
  private Player lastActor = null;

  public void onDialogRequest(final Player player) {
    if (!QuestEngine.getInstance().onDialog(new QuestEnv((VisibleObject)getOwner(), player, Integer.valueOf(0), Integer.valueOf(-1))))
      return; 
    int defaultUseTime = 3000;
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), getOwner().getObjectId(), 3000, 1));
    
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_QUESTLOOT, 0, getOwner().getObjectId()), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), ActionitemController.this.getOwner().getObjectId(), 3000, 0));
            
            ActionitemController.this.getOwner().setTarget((VisibleObject)player);
            ActionitemController.this.lastActor = player;
            ActionitemController.this.onDie((Creature)player);
          }
        }3000L);
  }

  public void doReward() {
    if (this.lastActor == null) {
      return;
    }
    DropService.getInstance().registerDrop(getOwner(), this.lastActor, this.lastActor.getLevel());
    DropService.getInstance().requestDropList(this.lastActor, getOwner().getObjectId());

    this.lastActor = null;
  }

  public void onRespawn() {
    super.onRespawn();
    DropService.getInstance().unregisterDrop(getOwner());
  }
}
