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
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _2014ScoutitOut extends QuestHandler {
  private static final int questId = 2014;

  public _2014ScoutitOut() {
    super(Integer.valueOf(2014));
  }

  public void register() {
    this.qe.addQuestLvlUp(2014);
    this.qe.setNpcQuestData(203606).addOnTalkEvent(2014);
    this.qe.setNpcQuestData(700009).addOnTalkEvent(2014);
    this.qe.setNpcQuestData(203633).addOnTalkEvent(2014);
    this.qe.setNpcQuestData(700135).addOnKillEvent(2014);
    this.qe.setNpcQuestData(203631).addOnTalkEvent(2014);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(2014);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {
        
        case 203606:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
              if (var == 1 || var == 2) {
                if (player.getInventory().getItemCountByItemId(182203015) == 0L) {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
                }
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              }  break;
            case 10000:
            case 10001:
              if (var == 0 || var == 1 || var == 2) {
                
                if (var == 1 || var == 2) {
                  
                  ItemService.removeItemFromInventoryByItemId(player, 182203015);
                  qs.setQuestVarById(0, 3);
                }
                else {
                  
                  qs.setQuestVarById(0, 1);
                }  updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 700009:
          switch (env.getDialogId().intValue()) {
            
            case -1:
              if (var == 1) {
                
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
                        
                        if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203015, 1)))) {
                          
                          qs.setQuestVarById(0, 2);
                          _2014ScoutitOut.this.updateQuestStatus(player, qs);
                        } 
                      }
                    }3000L);
                
                return true;
              }  break;
          } 
          break;
        case 203633:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 3)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              break;
            case 10002:
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203631)
      {
        return defaultQuestEndDialog(env);
      }
    } 
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2014);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId == 700135 && var == 4) {

      qs.setStatus(QuestStatus.REWARD);
      updateQuestStatus(player, qs);
      return true;
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2014);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}
