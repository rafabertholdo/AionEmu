package quest.ishalgen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _2123TheImprisonedGourmet extends QuestHandler {
  private static final int questId = 2123;

  public _2123TheImprisonedGourmet() {
    super(Integer.valueOf(2123));
  }

  public void register() {
    this.qe.setNpcQuestData(203550).addOnQuestStart(2123);
    this.qe.setNpcQuestData(203550).addOnTalkEvent(2123);
    this.qe.setNpcQuestData(700128).addOnTalkEvent(2123);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(2123);
    int targetId = 0;
    if (player.getCommonData().getLevel() < 7)
      return false; 
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (targetId == 203550) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
      
      if (qs.getStatus() == QuestStatus.START) {

        
        if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 0)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        }
        if (env.getDialogId().intValue() == 10000 && qs.getQuestVarById(0) == 0) {
          
          long itemCount = player.getInventory().getItemCountByItemId(182203121);
          if (itemCount > 0L) {
            
            ItemService.removeItemFromInventoryByItemId(player, 182004687);
            qs.setQuestVar(5);
            updateQuestStatus(player, qs);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          } 

          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        } 
        
        if (env.getDialogId().intValue() == 10001 && qs.getQuestVarById(0) == 0) {
          
          long itemCount = player.getInventory().getItemCountByItemId(182203122);
          if (itemCount > 0L) {
            
            ItemService.removeItemFromInventoryByItemId(player, 182203122);
            qs.setQuestVar(6);
            updateQuestStatus(player, qs);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          } 

          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        } 
        
        if (env.getDialogId().intValue() == 10002 && qs.getQuestVarById(0) == 0) {
          
          long itemCount = player.getInventory().getItemCountByItemId(182203123);
          if (itemCount > 0L) {
            
            ItemService.removeItemFromInventoryByItemId(player, 182203123);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            qs.setQuestVar(7);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
          } 

          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        } 

        
        return defaultQuestEndDialog(env);
      } 
      if (qs.getStatus() == QuestStatus.REWARD) {
        
        if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 5)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 6)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
        }
        if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 7)
        {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
        }

        
        return defaultQuestEndDialog(env);
      } 


      
      return defaultQuestEndDialog(env);
    } 
    
    if (targetId == 700128) {
      
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        
        int targetObjectId = env.getVisibleObject().getObjectId();
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              
              public void run()
              {
                qs.setQuestVar(0);
                _2123TheImprisonedGourmet.this.updateQuestStatus(player, qs);
              }
            }3000L);
        return true;
      } 

      
      return defaultQuestEndDialog(env);
    } 


    
    return defaultQuestEndDialog(env);
  }
}
