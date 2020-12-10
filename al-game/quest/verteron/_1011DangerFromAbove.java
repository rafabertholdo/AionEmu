package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;





















public class _1011DangerFromAbove
  extends QuestHandler
{
  private static final int questId = 1011;
  
  public _1011DangerFromAbove() {
    super(Integer.valueOf(1011));
  }


  
  public void register() {
    int[] talkNpcs = { 203109, 203122, 203109 };
    this.qe.setNpcQuestData(700091).addOnKillEvent(1011);
    this.qe.addQuestLvlUp(1011);
    for (int id : talkNpcs) {
      this.qe.setNpcQuestData(id).addOnTalkEvent(1011);
    }
  }

  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1011);
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
    if (targetId == 700091) {
      
      if (var > 0 && var < 4) {
        
        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
        updateQuestStatus(player, qs);
        return true;
      } 
      if (var == 4) {
        
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
        return true;
      } 
    } 
    return false;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1011);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203109) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          case 10000:
            if (var == 0) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
            break;
        } 
      } else if (targetId == 203122) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          case 1353:
            if (var == 1)
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 24)); 
            return false;
          case 10001:
            if (var == 1) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
            break;
        } 
      
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203109) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1011);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    QuestState qs2 = player.getQuestStateList().getQuestState(1130);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1011DangerFromAbove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
