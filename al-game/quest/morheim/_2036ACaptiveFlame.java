package quest.morheim;

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

public class _2036ACaptiveFlame extends QuestHandler {
  private static final int questId = 2036;
  private static final int[] npc_ids = new int[] { 204407, 204408, 700236, 204317 };

  public _2036ACaptiveFlame() {
    super(Integer.valueOf(2036));
  }

  public void register() {
    this.qe.addQuestLvlUp(2036);
    this.qe.setNpcQuestData(212878).addOnKillEvent(2036);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2036);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2036);
    boolean lvlCheck = QuestService.checkLevelRequirement(2036, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    QuestState qs2 = player.getQuestStateList().getQuestState(2035);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
     final Player player = env.getPlayer();
     QuestState qs = player.getQuestStateList().getQuestState(2036);
     if (qs == null) {
       return false;
     }
     int var = qs.getQuestVarById(0);
     int targetId = 0;
     if (env.getVisibleObject() instanceof Npc)
       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
     if (qs.getStatus() == QuestStatus.START) {
       
       switch (targetId) {
 
         
         case 204407:
           switch (env.getDialogId().intValue()) {
             
             case 25:
               if (var == 0)
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
               if (var == 4)
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
             case 10000:
               if (var == 0) {
                 
                 qs.setQuestVarById(0, var + 1);
                 updateQuestStatus(player, qs);
                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                 return true;
               } 
             case 33:
               if (var == 4) {
                 
                 if (QuestService.collectItemCheck(env, true)) {
                   
                   qs.setStatus(QuestStatus.REWARD);
                   updateQuestStatus(player, qs);
                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                 } 
                 
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
               } 
               break;
           } 
           break;
         case 204408:
           switch (env.getDialogId().intValue()) {
             
             case 25:
               if (var == 1)
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
             case 1353:
               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 79));
               break;
             case 10001:
               if (var == 1) {
                 
                 qs.setQuestVarById(0, var + 1);
                 updateQuestStatus(player, qs);
                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                 return true;
               }  break;
           } 
           break;
         case 700236:
           switch (env.getDialogId().intValue()) {
             
             case -1:
               if (player.getInventory().getItemCountByItemId(182204014) == 0L) {
                 
                 final int targetObjectId = env.getVisibleObject().getObjectId();
                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
                 
                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
                 
                 ThreadPoolManager.getInstance().schedule(new Runnable()
                     {
                       public void run()
                       {
                         Npc npc = (Npc)player.getTarget();
                         if (npc == null || npc.getObjectId() != targetObjectId)
                           return; 
                         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                         
                         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                         
                         ItemService.addItems(player, Collections.singletonList(new QuestItems(182204014, 1)));
                       }
                     }3000L);
                 
                 return false;
               } 
               break;
           } 
           break;
       } 
     } else if (qs.getStatus() == QuestStatus.REWARD) {
       
       if (targetId == 204317) {
         
         if (env.getDialogId().intValue() == -1) {
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
         }
         return defaultQuestEndDialog(env);
       } 
     } 
     return false;
   }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2036);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START)
      return false;
    switch (targetId) {

      case 212878:
        if (var == 2) {

          qs.setQuestVarById(0, var + 2);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\morheim\_2036ACaptiveFlame.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
