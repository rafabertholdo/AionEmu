package quest.altgard;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _2232TheBrokenHoneyJar extends QuestHandler {
  private static final int questId = 2232;

  public _2232TheBrokenHoneyJar() {
    super(Integer.valueOf(2232));
  }

  public void register() {
    this.qe.setNpcQuestData(203613).addOnQuestStart(2232);
    this.qe.setNpcQuestData(203613).addOnTalkEvent(2232);
    this.qe.setNpcQuestData(203622).addOnTalkEvent(2232);
    this.qe.setNpcQuestData(700061).addOnTalkEvent(2232);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(2232);
    if (targetId == 203613) {
      
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 33)
        {
          if (QuestService.collectItemCheck(env, true)) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          } 
          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        }
      
      } else if (qs.getStatus() == QuestStatus.REWARD && qs.getQuestVarById(0) == 1) {
        
        return defaultQuestEndDialog(env);
      }
    
    }
    else if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
      
      switch (targetId) {
        
        case 700061:
          qs.setQuestVarById(0, 1);
          updateQuestStatus(player, qs);
          
          if (qs.getQuestVarById(0) == 1 && env.getDialogId().intValue() == -1) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    if (!player.isTargeting(targetObjectId))
                      return; 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    
                    if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203224, 1)))) {
                      ((Npc)player.getTarget()).getController().onDie(null);
                    }
                  }
                }3000L);
          } 
          break;
      } 
    
    } else if (targetId == 203622) {
      
      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        if (env.getDialogId().intValue() == 10000)
        {
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }
      
      } 
    } else if (targetId == 203622) {
      
      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
        return true;
      }
    } 
    return false;
  }
}
