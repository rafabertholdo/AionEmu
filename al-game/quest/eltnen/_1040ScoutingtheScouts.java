package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class _1040ScoutingtheScouts
  extends QuestHandler
{
  private static final int questId = 1040;
  
  public _1040ScoutingtheScouts() {
    super(Integer.valueOf(1040));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1040);
    this.qe.setNpcQuestData(212010).addOnKillEvent(1040);
    this.qe.setNpcQuestData(204046).addOnKillEvent(1040);
    this.qe.setNpcQuestData(203989).addOnTalkEvent(1040);
    this.qe.setNpcQuestData(203901).addOnTalkEvent(1040);
    this.qe.setNpcQuestData(204020).addOnTalkEvent(1040);
    this.qe.setNpcQuestData(204024).addOnTalkEvent(1040);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1040);
    boolean lvlCheck = QuestService.checkLevelRequirement(1040, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1040);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START)
      return false; 
    if (targetId == 212010) {
      
      if (var > 0 && var < 4)
      {
        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
        updateQuestStatus(player, qs);
        return true;
      }
    
    } else if (targetId == 204046) {
      
      if (var > 7 && var < 9) {
        
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 36));
        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
        updateQuestStatus(player, qs);
        return true;
      } 
    } 
    return false;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1040);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203989) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {
      
      return false;
    } 
    if (targetId == 203989) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          return false;
        
        case 1013:
          if (var == 0)
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 183)); 
          return false;
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10001:
          if (var == 4) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 203901) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          return false;
        
        case 10002:
          if (var == 5) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 204020) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          if (var == 10)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
          return false;
        
        case 10003:
          if (var == 6) {
            
            TeleportService.teleportTo(player, 210020000, 2211.0F, 811.0F, 513.0F, 0);
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            return true;
          } 
        
        case 10006:
          if (var == 10) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          return false;
      } 
    
    } else if (targetId == 204024) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 7)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
          if (var == 9)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
          return false;
        
        case 10004:
          if (var == 7) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10005:
          if (var == 9) {
            
            TeleportService.teleportTo(player, 210020000, 1606.0F, 1529.0F, 318.0F, 0);
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            return true;
          } 
          return false;
      } 
    
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1040ScoutingtheScouts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
