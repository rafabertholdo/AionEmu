package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
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

public class _1037SecretsoftheTemple extends QuestHandler {
  private static final int questId = 1037;
  private static final int[] npc_ids = new int[] { 203965, 203967, 700151, 700154, 700150, 700153, 700152 };

  public _1037SecretsoftheTemple() {
    super(Integer.valueOf(1037));
  }

  public void register() {
    this.qe.addQuestLvlUp(1037);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1037);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1037);
    boolean lvlCheck = QuestService.checkLevelRequirement(1037, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(1037);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD)
    {
      if (targetId == 203965) {
        return defaultQuestEndDialog(env);
      }
    }
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    if (targetId == 203965) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
          }
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 

    
    } else if (targetId == 203967) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (var == 2) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
          }
        case 1694:
          if (var == 2 && QuestService.collectItemCheck(env, true)) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
          }
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10002:
          if (var == 2) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201027, 1)));
            return true;
          } 
          return false;
      } 
    
    } else if ((targetId == 700151 && var == 3) || (targetId == 700154 && var == 4) || (targetId == 700150 && var == 5) || (targetId == 700153 && var == 6) || (targetId == 700152 && var == 7)) {

      
      if (env.getDialogId().intValue() == -1) {
        
        final int targetObjectId = env.getVisibleObject().getObjectId();
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 33));
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                if (qs.getQuestVarById(0) == 7) {
                  
                  ItemService.decreaseItemCountByItemId(player, 182201027, 1L);
                  qs.setStatus(QuestStatus.REWARD);
                  _1037SecretsoftheTemple.this.updateQuestStatus(player, qs);
                  
                  return;
                } 
                qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                _1037SecretsoftheTemple.this.updateQuestStatus(player, qs);
              }
            }3000L);
      } 
      return false;
    } 
    return false;
  }
}
