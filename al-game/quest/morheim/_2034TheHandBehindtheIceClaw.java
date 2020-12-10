package quest.morheim;

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

public class _2034TheHandBehindtheIceClaw extends QuestHandler {
  private static final int questId = 2034;
  private static final int[] npc_ids = new int[] { 204303, 204332, 700246, 204301 };

  public _2034TheHandBehindtheIceClaw() {
    super(Integer.valueOf(2034));
  }

  public void register() {
    this.qe.addQuestLvlUp(2034);
    this.qe.setNpcQuestData(204417).addOnKillEvent(2034);
    this.qe.setNpcQuestData(212877).addOnKillEvent(2034);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2034);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2034);
    boolean lvlCheck = QuestService.checkLevelRequirement(2034, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(2300);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
     final Player player = env.getPlayer();
     QuestState qs = player.getQuestStateList().getQuestState(2034);
     if (qs == null) {
       return false;
     }
     int var = qs.getQuestVarById(0);
     int targetId = 0;
     if (env.getVisibleObject() instanceof Npc) {
       targetId = ((Npc)env.getVisibleObject()).getNpcId();
     }
     if (qs.getStatus() == QuestStatus.REWARD) {
       
       if (targetId == 204301) {
         
         if (env.getDialogId().intValue() == -1)
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
         if (env.getDialogId().intValue() == 1009)
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
         return defaultQuestEndDialog(env);
       } 
       return false;
     } 
     if (qs.getStatus() != QuestStatus.START)
     {
       return false;
     }
     if (targetId == 204303) {
       
       switch (env.getDialogId().intValue()) {
         
         case 25:
           if (var == 0)
             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
           if (var == 5)
             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
         case 10000:
           if (var == 0) {
             
             qs.setQuestVarById(0, var + 1);
             updateQuestStatus(player, qs);
             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
             return true;
           } 
         case 10255:
           if (var == 5) {
             
             qs.setStatus(QuestStatus.REWARD);
             updateQuestStatus(player, qs);
             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
             return true;
           } 
           break;
       } 
     } else if (targetId == 204332) {
       
       switch (env.getDialogId().intValue()) {
         
         case 25:
           if (var == 1)
             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
           if (var == 2) {
             
             if (player.getInventory().getItemCountByItemId(182204008) == 0L) {
               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
             }
             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
           } 
           if (var == 3)
             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
         case 10001:
           if (var == 1) {
             
             qs.setQuestVarById(0, var + 1);
             updateQuestStatus(player, qs);
           } 
           ItemService.addItems(player, Collections.singletonList(new QuestItems(182204008, 1)));
           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
           return true;
         case 10003:
           if (var == 3) {
             
             qs.setQuestVarById(0, var + 1);
             updateQuestStatus(player, qs);
             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
             return true;
           } 
           break;
       } 
     } else if (targetId == 700246 && player.getInventory().getItemCountByItemId(182204019) >= 1L) {
       
       if (env.getDialogId().intValue() == -1 && var == 2) {
         
         final int targetObjectId = env.getVisibleObject().getObjectId();
         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
         ThreadPoolManager.getInstance().schedule(new Runnable()
             {
               public void run()
               {
                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                 QuestService.addNewSpawn(220020000, 1, 204417, 461.23F, 188.82F, 485.61F, (byte)25, true);
                 
                 ItemService.removeItemFromInventoryByItemId(player, 182204008);
                 ItemService.removeItemFromInventoryByItemId(player, 182204019);
               }
             }3000L);
       } 
     } 
     return false;
   }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2034);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId == 204417 && qs.getQuestVarById(0) == 2) {

      player.getTitleList().addTitle(58);
      qs.setQuestVarById(0, 3);
      updateQuestStatus(player, qs);
    }
    if (targetId == 212877 && qs.getQuestVarById(0) == 4) {

      qs.setQuestVarById(0, 5);
      updateQuestStatus(player, qs);
    }
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\morheim\_2034TheHandBehindtheIceClaw.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */
