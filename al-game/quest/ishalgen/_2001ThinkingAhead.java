package quest.ishalgen;

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
import com.aionemu.gameserver.utils.PacketSendUtility;


















public class _2001ThinkingAhead
  extends QuestHandler
{
  private static final int questId = 2001;
  
  public _2001ThinkingAhead() {
    super(Integer.valueOf(2001));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2001);
    this.qe.setNpcQuestData(203518).addOnTalkEvent(2001);
    this.qe.setNpcQuestData(700093).addOnTalkEvent(2001);
    this.qe.setNpcQuestData(210369).addOnKillEvent(2001);
    this.qe.setNpcQuestData(210368).addOnKillEvent(2001);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2001);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2001);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203518) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            if (var == 2)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694); 
            return false;
          case 1012:
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 51));
            break;
          case 10000:
          case 10002:
            if (var == 0 || var == 2) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
          case 33:
            if (var == 1) {
              
              if (QuestService.collectItemCheck(env, true)) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
              } 
              
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
            } 
            break;
        } 
      } else if (targetId == 700093) {
        
        if (var == 1 && env.getDialogId().intValue() == -1) {
          return true;
        }
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203518) {
        
        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        }
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2001);
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
    switch (targetId) {
      
      case 210368:
      case 210369:
        if (var >= 3 && var < 8) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return true;
        } 
        if (var == 8) {
          
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return true;
        }  break;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2001ThinkingAhead.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
