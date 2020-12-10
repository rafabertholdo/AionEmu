package quest.theobomos;

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




















public class _1092JosnacksDilemma
  extends QuestHandler
{
  private static final int questId = 1092;
  private static final int[] npc_ids = new int[] { 798155, 798206, 700390, 700388, 700389 };

  
  public _1092JosnacksDilemma() {
    super(Integer.valueOf(1092));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1092);
    for (int npc_id : npc_ids)
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1092); 
    this.deletebleItems = new int[] { 182208012 };
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1092);
    boolean lvlCheck = QuestService.checkLevelRequirement(1092, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(1091);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1092);
    Npc npc = (Npc)env.getVisibleObject();
    
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 798155)
        return defaultQuestEndDialog(env); 
      return false;
    } 
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    if (targetId == 798155) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
          return true;
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10003:
          if (var == 3) {
            
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
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
            } 
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10008);
          } 
          break;
      } 
    } else if (targetId == 798206) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 700388) {
      
      switch (env.getDialogId().intValue()) {
        
        case -1:
          if (var == 2) {
            
            updateQuestStatus(player, qs);
            return true;
          } 
          break;
      } 
    } else if (targetId == 700389) {
      
      switch (env.getDialogId().intValue()) {
        
        case -1:
          if (var == 2) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            return true;
          } 
          break;
      } 
    } else if (targetId == 700390) {
      
      switch (env.getDialogId().intValue()) {
        
        case -1:
          if (var == 4) {
            
            QuestService.addNewSpawn(210070000, 6, 214552, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
            
            npc.getController().onDespawn(true);
            npc.getController().scheduleRespawn();
            return true;
          }  break;
      } 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_1092JosnacksDilemma.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
