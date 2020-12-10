package quest.ascension;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TELEPORT_LOC;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class _1007ACeremonyinSanctum
  extends QuestHandler
{
  private static final int questId = 1007;
  
  public _1007ACeremonyinSanctum() {
    super(Integer.valueOf(1007));
  }


  
  public void register() {
    if (CustomConfig.ENABLE_SIMPLE_2NDCLASS)
      return; 
    this.qe.addQuestLvlUp(1007);
    this.qe.setNpcQuestData(790001).addOnTalkEvent(1007);
    this.qe.setNpcQuestData(203725).addOnTalkEvent(1007);
    this.qe.setNpcQuestData(203752).addOnTalkEvent(1007);
    this.qe.setNpcQuestData(203758).addOnTalkEvent(1007);
    this.qe.setNpcQuestData(203759).addOnTalkEvent(1007);
    this.qe.setNpcQuestData(203760).addOnTalkEvent(1007);
    this.qe.setNpcQuestData(203761).addOnTalkEvent(1007);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1007);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVars().getQuestVars();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 790001) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          case 10000:
            if (var == 0) {
              
              qs.setQuestVar(1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_TELEPORT_LOC(110010000, 1313.0F, 1512.0F, 568.0F));
              TeleportService.scheduleTeleportTask(player, 110010000, 1313.0F, 1512.0F, 568.0F);
              return true;
            } 
            break;
        } 
      } else if (targetId == 203725) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          case 1353:
            if (var == 1) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 92));
              return false;
            } 
          case 10001:
            if (var == 1) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
            break;
        } 
      } else if (targetId == 203752) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 2)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          case 1694:
            if (var == 2) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 91));
              return false;
            } 
          case 10002:
            if (var == 2) {
              
              PlayerClass playerClass = PlayerClass.getStartingClassFor(player.getCommonData().getPlayerClass());
              if (playerClass == PlayerClass.WARRIOR) {
                qs.setQuestVar(10);
              } else if (playerClass == PlayerClass.SCOUT) {
                qs.setQuestVar(20);
              } else if (playerClass == PlayerClass.MAGE) {
                qs.setQuestVar(30);
              } else if (playerClass == PlayerClass.PRIEST) {
                qs.setQuestVar(40);
              }  qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
            break;
        } 
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203758 && var == 10) {
        
        switch (env.getDialogId().intValue()) {
          
          case -1:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
          case 1009:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          case 8:
          case 9:
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
          case 15:
          case 16:
          case 17:
            if (QuestService.questFinish(env, 0)) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            break;
        } 
      } else if (targetId == 203759 && var == 20) {
        
        switch (env.getDialogId().intValue()) {
          
          case -1:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
          case 1009:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          case 8:
          case 9:
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
          case 15:
          case 16:
          case 17:
            if (QuestService.questFinish(env, 1)) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            break;
        } 
      } else if (targetId == 203760 && var == 30) {
        
        switch (env.getDialogId().intValue()) {
          
          case -1:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
          case 1009:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
          case 8:
          case 9:
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
          case 15:
          case 16:
          case 17:
            if (QuestService.questFinish(env, 2)) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            break;
        } 
      } else if (targetId == 203761 && var == 40) {
        
        switch (env.getDialogId().intValue()) {
          
          case -1:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
          case 1009:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 8);
          case 8:
          case 9:
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
          case 15:
          case 16:
          case 17:
            if (QuestService.questFinish(env, 3)) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }  break;
        } 
      } 
    } 
    return false;
  }

  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1007);
    if (qs != null) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(1006);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false; 
    env.setQuestId(Integer.valueOf(1007));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ascension\_1007ACeremonyinSanctum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
