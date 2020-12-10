package quest.morheim;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _2038ALostDaeva extends QuestHandler {
  private static final int questId = 2038;
  private static final int[] npc_ids = new int[] { 204342, 204053, 700233 };

  public _2038ALostDaeva() {
    super(Integer.valueOf(2038));
  }

  public void register() {
    this.qe.addQuestLvlUp(2038);
    this.qe.addOnDie(2038);
    this.qe.setNpcQuestData(212879).addOnKillEvent(2038);
    this.qe.setQuestItemIds(182204016).add(2038);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2038);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2038);
    boolean lvlCheck = QuestService.checkLevelRequirement(2038, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
     final Player player = env.getPlayer();
     final QuestState qs = player.getQuestStateList().getQuestState(2038);
     if (qs == null) {
       return false;
     }
     int var = qs.getQuestVarById(0);
     int targetId = 0;
     if (env.getVisibleObject() instanceof Npc)
       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
     if (qs.getStatus() == QuestStatus.START) {
       
       switch (targetId) {
 
         
         case 204342:
           switch (env.getDialogId().intValue()) {
             
             case 25:
               if (var == 0)
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
               if (var == 4)
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
             case 1012:
               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 82));
               break;
             case 10000:
               if (var == 0) {
                 
                 qs.setQuestVarById(0, var + 1);
                 updateQuestStatus(player, qs);
                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                 return true;
               } 
             case 10255:
               if (var == 4) {
                 
                 qs.setStatus(QuestStatus.REWARD);
                 updateQuestStatus(player, qs);
                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                 return true;
               } 
               break;
           } 
           break;
         case 700233:
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
                         Npc npc = (Npc)player.getTarget();
                         if (npc == null || npc.getObjectId() != targetObjectId)
                           return; 
                         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                         
                         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
 
                         
                         qs.setQuestVarById(0, 2);
                         _2038ALostDaeva.this.updateQuestStatus(player, qs);
                       }
                     }3000L);
                 
                 return false;
               } 
               break;
           } 
           break;
       } 
     } else if (qs.getStatus() == QuestStatus.REWARD) {
       
       if (targetId == 204053) {
         
         if (env.getDialogId().intValue() == -1) {
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
         }
         return defaultQuestEndDialog(env);
       } 
     } 
     return false;
   }

  public boolean onDieEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2038);
    if (qs == null || qs.getStatus() != QuestStatus.START)
      return false;
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.WONSHIKUTZS_LABORATORY_220020000))
      return false;
    int var = qs.getQuestVars().getQuestVars();
    if (var == 1 || var == 2) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 83));
      return true;
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2038);
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

      case 212879:
        if (var == 2) {

          qs.setQuestVarById(0, var + 2);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
     final Player player = env.getPlayer();
     final int id = item.getItemTemplate().getTemplateId();
     final int itemObjId = item.getObjectId();
     
     if (id != 182204016)
       return false; 
     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 1000, 0, 0), true);
     ThreadPoolManager.getInstance().schedule(new Runnable()
         {
           public void run()
           {
             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
           }
         }1000L);
     return true;
   }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\morheim\_2038ALostDaeva.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */