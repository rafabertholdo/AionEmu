package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _3930SecretoftheShatteredStigma extends QuestHandler {
  private static final int questId = 3930;

  public _3930SecretoftheShatteredStigma() {
    super(Integer.valueOf(3930));
  }

  public void register() {
    this.qe.setNpcQuestData(203711).addOnQuestStart(3930);
    this.qe.setNpcQuestData(203833).addOnTalkEvent(3930);
    this.qe.setNpcQuestData(798321).addOnTalkEvent(3930);
    this.qe.setNpcQuestData(700562).addOnTalkEvent(3930);
    this.qe.setNpcQuestData(203711).addOnTalkEvent(3930);
  }

  public boolean onDialogEvent(QuestEnv env) {
     final Player player = env.getPlayer();
     int targetId = 0;
     if (env.getVisibleObject() instanceof Npc)
       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
     final QuestState qs = player.getQuestStateList().getQuestState(3930);
 
 
 
     
     if (qs == null || qs.getStatus() == QuestStatus.NONE)
     {
       if (targetId == 203711) {
 
         
         if (env.getDialogId().intValue() == 25)
         {
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
         }
         return defaultQuestStartDialog(env);
       } 
     }
 
     
     if (qs == null) {
       return false;
     }
     int var = qs.getQuestVarById(0);
     
     if (qs.getStatus() == QuestStatus.START) {
 
       
       switch (targetId) {
 
 
         
         case 203833:
           if (var == 0)
           {
             switch (env.getDialogId().intValue()) {
 
 
               
               case 25:
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
               
               case 10000:
                 qs.setQuestVar(1);
                 updateQuestStatus(player, qs);
                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                 return true;
             } 
           
           }
         case 798321:
           if (var == 1) {
             
             switch (env.getDialogId().intValue()) {
 
 
               
               case 25:
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
               
               case 10001:
                 qs.setQuestVar(2);
                 updateQuestStatus(player, qs);
                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                 return true;
             } 
           
           } else if (var == 2) {
             
             switch (env.getDialogId().intValue()) {
 
 
               
               case 25:
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
               
               case 33:
                 if (player.getInventory().getItemCountByItemId(182206075) < 1L)
                 {
                   
                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
                 }
                 ItemService.removeItemFromInventoryByItemId(player, 182206075);
                 qs.setStatus(QuestStatus.REWARD);
                 updateQuestStatus(player, qs);
                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
             } 
           
           } 
           return false;
         case 700562:
           if (var == 2) {
             
             ThreadPoolManager.getInstance().schedule(new Runnable()
                 {
                   public void run()
                   {
                     _3930SecretoftheShatteredStigma.this.updateQuestStatus(player, qs);
                   }
                 }3000L);
             return true;
           } 
           break;
       } 
     
     } else if (qs.getStatus() == QuestStatus.REWARD) {
       
       if (targetId == 203711) {
         
         if (env.getDialogId().intValue() == -1)
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
         if (env.getDialogId().intValue() == 1009)
           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
         return defaultQuestEndDialog(env);
       } 
     } 
     return false;
   }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\sanctum\_3930SecretoftheShatteredStigma.class Java compiler version:
 * 6 (50.0) JD-Core Version: 1.1.3
 */