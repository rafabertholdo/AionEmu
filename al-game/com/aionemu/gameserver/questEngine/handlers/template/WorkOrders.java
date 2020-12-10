package com.aionemu.gameserver.questEngine.handlers.template;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.CollectItem;
import com.aionemu.gameserver.model.templates.quest.CollectItems;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.handlers.models.WorkOrdersData;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;






















public class WorkOrders
  extends QuestHandler
{
  private final WorkOrdersData workOrdersData;
  
  public WorkOrders(WorkOrdersData workOrdersData) {
    super(Integer.valueOf(workOrdersData.getId()));
    this.workOrdersData = workOrdersData;
  }


  
  public void register() {
    this.qe.setNpcQuestData(this.workOrdersData.getStartNpcId()).addOnQuestStart(this.workOrdersData.getId());
    this.qe.setNpcQuestData(this.workOrdersData.getStartNpcId()).addOnTalkEvent(this.workOrdersData.getId());
    this.qe.addOnQuestAbort(this.workOrdersData.getId());
    this.qe.addOnQuestFinish(this.workOrdersData.getId());
    int i = 0;
    CollectItems collectItems = DataManager.QUEST_DATA.getQuestById(this.workOrdersData.getId()).getCollectItems();
    int count = 0;
    if (collectItems != null)
    {
      count = collectItems.getCollectItem().size();
    }
    this.deletebleItems = new int[count + this.workOrdersData.getGiveComponent().size()];
    for (QuestItems questItem : this.workOrdersData.getGiveComponent())
    {
      this.deletebleItems[i++] = questItem.getItemId().intValue();
    }
    if (collectItems != null)
    {
      for (CollectItem item : collectItems.getCollectItem())
      {
        this.deletebleItems[i++] = item.getItemId().intValue();
      }
    }
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (targetId != this.workOrdersData.getStartNpcId())
      return false; 
    QuestState qs = player.getQuestStateList().getQuestState(this.workOrdersData.getId());
    if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.getStatus() == QuestStatus.COMPLETE) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4);
        case 1002:
          if (QuestService.startQuest(env, QuestStatus.START)) {
            
            if (ItemService.addItems(player, this.workOrdersData.getGiveComponent())) {
              
              player.getRecipeList().addRecipe(player, DataManager.RECIPE_DATA.getRecipeTemplateById(this.workOrdersData.getRecipeId()));
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
            } 
            return true;
          } 
          break;
      } 
    } else if (qs != null && qs.getStatus() == QuestStatus.START) {
      
      if (env.getDialogId().intValue() == 25)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
      if (env.getDialogId().intValue() == 17)
      {
        if (QuestService.collectItemCheck(env, true)) {

          
          qs.setStatus(QuestStatus.COMPLETE);
          abortQuest(env);
          qs.setCompliteCount(qs.getCompliteCount() + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
          return true;
        } 
      }
    } 
    return false;
  }


  
  public boolean onQuestFinishEvent(QuestEnv env) {
    deleteQuestItems(env);
    return true;
  }


  
  public boolean onQuestAbortEvent(QuestEnv env) {
    abortQuest(env);
    return true;
  }

  
  private void abortQuest(QuestEnv env) {
    env.getPlayer().getRecipeList().deleteRecipe(env.getPlayer(), this.workOrdersData.getRecipeId());
    deleteQuestItems(env);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\template\WorkOrders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
