package quest.brusthonin;

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




















public class _2094TheSecretofAdmaStronghold
  extends QuestHandler
{
  private static final int questId = 2094;
  private static final int[] npc_ids = new int[] { 205150, 205192, 205155, 730164, 205191, 204057 };

  
  public _2094TheSecretofAdmaStronghold() {
    super(Integer.valueOf(2094));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2094);
    this.qe.setNpcQuestData(214700).addOnKillEvent(2094);
    for (int npc_id : npc_ids)
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2094); 
    this.deletebleItems = new int[] { 182209013 };
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2094);
    boolean lvlCheck = QuestService.checkLevelRequirement(2094, player.getCommonData().getLevel());
    
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
    {
      return false;
    }
    
    int[] quests = { 2092, 2093, 2054 };
    for (int id : quests) {
      
      QuestState qs2 = player.getQuestStateList().getQuestState(id);
      if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
        return false;
      }
    } 
    
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2094);
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
      
      if (targetId == 204057)
        return defaultQuestEndDialog(env); 
      return false;
    } 
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    if (targetId == 205150) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          return true;
        case 10000:
          if (var == 0) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 205192) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          return true;
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 33:
          if (var == 2) {
            
            if (QuestService.collectItemCheck(env, true)) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
            } 
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10008);
          } 
        case 10003:
          if (var == 3) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
          } 
          break;
      } 
    } else if (targetId == 205155) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
        case 10005:
          if (var == 5) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 730164) {
      
      switch (env.getDialogId().intValue()) {
        
        case -1:
          if (var == 6) {
            
            QuestService.addNewSpawn(220050000, 1, 205191, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
            
            npc.getController().onDespawn(true);
            npc.getController().scheduleRespawn();
            return true;
          } 
          break;
      } 
    } else if (targetId == 205191) {
      
      switch (env.getDialogId().intValue()) {
        
        case -1:
          if (var == 6) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return true;
          }  break;
      } 
    } 
    return false;
  }



  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2094);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (targetId == 214700)
    {
      if (var == 4) {
        
        qs.setQuestVarById(0, var + 1);
        updateQuestStatus(player, qs);
        return true;
      } 
    }
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_2094TheSecretofAdmaStronghold.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
