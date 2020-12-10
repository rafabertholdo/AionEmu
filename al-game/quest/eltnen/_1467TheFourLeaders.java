package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class _1467TheFourLeaders
  extends QuestHandler
{
  private static final int questId = 1467;
  
  public _1467TheFourLeaders() {
    super(Integer.valueOf(1467));
  }


  
  public void register() {
    this.qe.setNpcQuestData(204045).addOnQuestStart(1467);
    this.qe.setNpcQuestData(204045).addOnTalkEvent(1467);
    this.qe.setNpcQuestData(211696).addOnKillEvent(1467);
    this.qe.setNpcQuestData(211697).addOnKillEvent(1467);
    this.qe.setNpcQuestData(211698).addOnKillEvent(1467);
    this.qe.setNpcQuestData(211699).addOnKillEvent(1467);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1467);
    
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
    {
      if (targetId == 204045) {
        
        switch (env.getDialogId().intValue()) {

          
          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);

          
          case 1002:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);

          
          case 10000:
            QuestService.startQuest(env, QuestStatus.START);
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;

          
          case 10001:
            QuestService.startQuest(env, QuestStatus.START);
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 2);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;

          
          case 10002:
            QuestService.startQuest(env, QuestStatus.START);
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 3);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;

          
          case 10003:
            QuestService.startQuest(env, QuestStatus.START);
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 4);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
        } 
        
        return defaultQuestStartDialog(env);
      } 
    }

    
    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.REWARD)
    {
      if (targetId == 204045)
      {
        switch (env.getDialogId().intValue()) {

          
          case -1:
            switch (qs.getQuestVarById(0)) {

              
              case 1:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);

              
              case 2:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);

              
              case 3:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);

              
              case 4:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 8);
            } 
          
          
          
          case 17:
            QuestService.questFinish(env, qs.getQuestVarById(0) - 1);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
        } 
      
      }
    }
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1467);
    
    if (qs == null || qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    
    int var = 0;
    int targetId = 0;
    
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    switch (targetId) {

      
      case 211696:
        if (qs.getQuestVarById(0) == 1)
        {
          if (var == 0) {
            
            var = 1;
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return true;
          } 
        }

      
      case 211697:
        if (qs.getQuestVarById(0) == 2)
        {
          if (var == 0) {
            
            var = 1;
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return true;
          } 
        }

      
      case 211698:
        if (qs.getQuestVarById(0) == 3)
        {
          if (var == 0) {
            
            var = 1;
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return true;
          } 
        }

      
      case 211699:
        if (qs.getQuestVarById(0) == 4)
        {
          if (var == 0) {
            
            var = 1;
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return true;
          } 
        }
        break;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1467TheFourLeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
