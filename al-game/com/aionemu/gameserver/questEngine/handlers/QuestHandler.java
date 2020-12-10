package com.aionemu.gameserver.questEngine.handlers;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.model.templates.quest.QuestWorkItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneName;





























public class QuestHandler
{
  private final Integer questId;
  protected QuestEngine qe;
  protected int[] deletebleItems;
  
  protected QuestHandler(Integer questId) {
    this.questId = questId;
    this.qe = QuestEngine.getInstance();
  }

  
  public void deleteQuestItems(QuestEnv env) {
    QuestWorkItems qwi = DataManager.QUEST_DATA.getQuestById(env.getQuestId().intValue()).getQuestWorkItems();
    
    Player player = env.getPlayer();
    if (qwi != null)
    {
      for (QuestItems qi : qwi.getQuestWorkItem()) {
        
        if (qi != null)
        {
          ItemService.removeItemFromInventoryByItemId(player, qi.getItemId().intValue());
        }
      } 
    }
    for (int itemId : this.deletebleItems)
    {
      ItemService.removeItemFromInventoryByItemId(player, itemId);
    }
  }

  
  public synchronized void updateQuestStatus(Player player, QuestState qs) {
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(this.questId.intValue(), qs.getStatus(), qs.getQuestVars().getQuestVars()));
    if (qs.getStatus() == QuestStatus.COMPLETE) {
      player.getController().updateNearbyQuests();
    }
  }
  
  public boolean sendQuestDialog(Player player, int objId, int dialogId) {
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(objId, dialogId, this.questId.intValue()));
    return true;
  }


  
  public boolean defaultQuestStartDialog(QuestEnv env) {
    Player player = env.getPlayer();
    
    int targetObjId = (env.getVisibleObject() == null) ? 0 : env.getVisibleObject().getObjectId();
    switch (env.getDialogId().intValue()) {
      
      case 1007:
        return sendQuestDialog(player, targetObjId, 4);
      case 1002:
        if (QuestService.startQuest(env, QuestStatus.START)) {
          return sendQuestDialog(player, targetObjId, 1003);
        }
        return false;
      case 1003:
        return sendQuestDialog(player, targetObjId, 1004);
    } 
    return false;
  }
  
  public boolean defaultQuestEndDialog(QuestEnv env) {
    QuestState qs;
    Player player = env.getPlayer();
    int targetObjId = (env.getVisibleObject() == null) ? 0 : env.getVisibleObject().getObjectId();
    switch (env.getDialogId().intValue()) {
      
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
        if (QuestService.questFinish(env)) {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjId, 10));
          return true;
        } 
        return false;
      case -1:
      case 1009:
        qs = player.getQuestStateList().getQuestState(this.questId.intValue());
        if (qs != null && qs.getStatus() == QuestStatus.REWARD)
        {
          return sendQuestDialog(player, targetObjId, 5); } 
        break;
    } 
    return false;
  }



  
  public Integer getQuestId() {
    return this.questId;
  }

  
  public boolean onDialogEvent(QuestEnv questEnv) {
    return false;
  }

  
  public boolean onEnterWorldEvent(QuestEnv questEnv) {
    return false;
  }

  
  public boolean onEnterZoneEvent(QuestEnv questEnv, ZoneName zoneName) {
    return false;
  }

  
  public boolean onItemUseEvent(QuestEnv questEnv, Item item) {
    return false;
  }

  
  public boolean onKillEvent(QuestEnv questEnv) {
    return false;
  }

  
  public boolean onAttackEvent(QuestEnv questEnv) {
    return false;
  }

  
  public boolean onLvlUpEvent(QuestEnv questEnv) {
    return false;
  }

  
  public boolean onDieEvent(QuestEnv questEnv) {
    return false;
  }

  
  public boolean onMovieEndEvent(QuestEnv questEnv, int movieId) {
    return false;
  }

  
  public boolean onQuestFinishEvent(QuestEnv questEnv) {
    return false;
  }

  
  public boolean onQuestAbortEvent(QuestEnv questEnv) {
    return false;
  }

  
  public boolean onQuestTimerEndEvent(QuestEnv questEnv) {
    return false;
  }
  
  public void register() {}
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\QuestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
