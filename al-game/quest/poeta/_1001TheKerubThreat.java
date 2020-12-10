package quest.poeta;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;




















public class _1001TheKerubThreat
  extends QuestHandler
{
  private static final int questId = 1001;
  
  public _1001TheKerubThreat() {
    super(Integer.valueOf(1001));
  }


  
  public void register() {
    this.qe.setNpcQuestData(210670).addOnKillEvent(1001);
    this.qe.setNpcQuestData(203071).addOnTalkEvent(1001);
    this.qe.setNpcQuestData(203067).addOnTalkEvent(1001);
    this.qe.addQuestLvlUp(1001);
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1001);
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
    if (targetId == 210670)
    {
      if (var > 0 && var < 6) {
        
        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
        updateQuestStatus(player, qs);
        return true;
      } 
    }
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1001);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1001);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203071)
      {
        switch (env.getDialogId().intValue()) {
          
          case 1012:
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 15));
            return false;
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
            if (var == 6)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            if (var == 7)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
            return false;
          case 33:
          case 10002:
            if (var == 7) {
              
              long itemCount = player.getInventory().getItemCountByItemId(182200001);
              if (itemCount >= 5L) {
                
                if (env.getDialogId().intValue() == 33)
                {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
                }

                
                ItemService.removeItemFromInventoryByItemId(player, 182200001);
                qs.setQuestVarById(0, var + 1);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 

              
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
            } 
            return true;
          case 10000:
          case 10001:
            if (var == 0 || var == 6) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            } 
            return true;
        } 
        return false;
      }
    
    }
    else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203067)
      {
        return defaultQuestEndDialog(env);
      }
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1001TheKerubThreat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
