package quest.ishalgen;

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





















public class _2098ButWhatweMake
  extends QuestHandler
{
  private static final int questId = 2098;
  
  public _2098ButWhatweMake() {
    super(Integer.valueOf(2098));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2098);
    this.qe.setNpcQuestData(203550).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204361).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204408).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(205198).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204805).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204808).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(203546).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204387).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(205190).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204207).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204301).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(205155).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204784).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(278001).addOnTalkEvent(2098);
    this.qe.setNpcQuestData(204053).addOnTalkEvent(2098);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2098);
    if (qs != null)
      return false; 
    boolean lvlCheck = QuestService.checkLevelRequirement(2098, player.getCommonData().getLevel());
    if (!lvlCheck)
      return false; 
    env.setQuestId(Integer.valueOf(2098));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2098);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVars().getQuestVars();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {
        
        case 203550:
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
              }  break;
          } 
          break;
        case 204361:
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
              }  break;
          } 
          break;
        case 204408:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              return true;
            case 10002:
              if (var == 2) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 205198:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 3)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
              return true;
            case 10003:
              if (var == 3) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 204805:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 4)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
              return true;
            case 10004:
              if (var == 4) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 204808:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 5)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
              return true;
            case 10005:
              if (var == 5) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 203546:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 6)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
              return true;
            case 10006:
              if (var == 6) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 204387:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 7)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
              return true;
            case 10007:
              if (var == 7) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 205190:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 8)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
              return true;
            case 10008:
              if (var == 8) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 204207:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 9)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
              return true;
            case 10009:
              if (var == 9) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 204301:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 10)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608); 
              return true;
            case 10010:
              if (var == 10) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 205155:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 11)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1949); 
              return true;
            case 10011:
              if (var == 11) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 204784:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 12)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2290); 
              return true;
            case 10012:
              if (var == 12) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 278001:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 13)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2631); 
              return true;
            case 10013:
              if (var == 13) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 204053:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 14)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2972); 
              return true;
            case 10255:
              if (var == 14) {
                
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              } 
              break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD && targetId == 203550) {
      return defaultQuestEndDialog(env);
    }  return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2098ButWhatweMake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
