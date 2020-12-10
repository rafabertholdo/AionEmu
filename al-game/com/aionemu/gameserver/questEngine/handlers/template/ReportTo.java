package com.aionemu.gameserver.questEngine.handlers.template;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import java.util.Collections;



























public class ReportTo
  extends QuestHandler
{
  private final int questId;
  private final int startNpc;
  private final int endNpc;
  private final int itemId;
  
  public ReportTo(int questId, int startNpc, int endNpc, int itemId) {
    super(Integer.valueOf(questId));
    this.startNpc = startNpc;
    this.endNpc = endNpc;
    this.questId = questId;
    this.itemId = itemId;
  }


  
  public void register() {
    this.qe.setNpcQuestData(this.startNpc).addOnQuestStart(this.questId);
    this.qe.setNpcQuestData(this.startNpc).addOnTalkEvent(this.questId);
    this.qe.setNpcQuestData(this.endNpc).addOnTalkEvent(this.questId);
    this.deletebleItems = new int[] { this.itemId };
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(this.questId);
    QuestTemplate template = DataManager.QUEST_DATA.getQuestById(this.questId);
    if (targetId == this.startNpc) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE || (qs.getStatus() == QuestStatus.COMPLETE && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue()))
      {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 1002 && this.itemId != 0) {
          
          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(this.itemId, 1))))
            return defaultQuestStartDialog(env); 
          return true;
        } 
        
        return defaultQuestStartDialog(env);
      }
    
    } else if (targetId == this.endNpc) {
      
      if (qs != null) {
        
        if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 1009) {
          
          if (this.itemId != 0)
            ItemService.removeItemFromInventoryByItemId(player, this.itemId); 
          qs.setQuestVar(1);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        } 
        
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\template\ReportTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
