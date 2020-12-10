package quest.verteron;

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
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _1158VillageSealFound extends QuestHandler {
  private static final int questId = 1158;

  public _1158VillageSealFound() {
    super(Integer.valueOf(1158));
  }

  public void register() {
    this.qe.setNpcQuestData(798003).addOnQuestStart(1158);
    this.qe.setNpcQuestData(798003).addOnTalkEvent(1158);
    this.qe.setNpcQuestData(700003).addOnTalkEvent(1158);
    this.qe.setNpcQuestData(203128).addOnTalkEvent(1158);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1158);
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
    {
      if (targetId == 798003) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
    }
    
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203128) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
        return defaultQuestEndDialog(env);
      } 
      return false;
    } 
    if (qs.getStatus() == QuestStatus.START)
    {
      if (targetId == 700003 && var == 0) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        if (env.getDialogId().intValue() == 10000) {
          
          qs.setQuestVarById(0, 1);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          ItemService.addItems(player, Collections.singletonList(new QuestItems(182200502, 1)));
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        } 
        if (env.getDialogId().intValue() == 1353) {
          
          final int targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                  _1158VillageSealFound.this.sendQuestDialog(player, targetObjectId, 1353);
                }
              }3000L);
        } 
      } 
    }
    
    return false;
  }
}
