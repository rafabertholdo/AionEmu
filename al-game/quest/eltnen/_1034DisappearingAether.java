package quest.eltnen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _1034DisappearingAether extends QuestHandler {
  private static final int questId = 1034;

  public _1034DisappearingAether() {
    super(Integer.valueOf(1034));
  }

  public void register() {
    this.qe.addQuestLvlUp(1034);
    this.qe.setNpcQuestData(203903).addOnTalkEvent(1034);
    this.qe.setNpcQuestData(204501).addOnTalkEvent(1034);
    this.qe.setNpcQuestData(700149).addOnTalkEvent(1034);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1034);
    boolean lvlCheck = QuestService.checkLevelRequirement(1034, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(1300);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(1034);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203903) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
        return defaultQuestEndDialog(env);
      } 
      return false;
    } 
    if (qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    if (targetId == 203903) {
      
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
    } else if (targetId == 204032) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        case 33:
          if (QuestService.collectItemCheck(env, true)) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
          } 
          
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
        case 1353:
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 179));
          break;
        case 10001:
          if (var == 1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
        case 10002:
          if (var == 3) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    } else if (targetId == 700149 && var == 2) {
      
      if (env.getDialogId().intValue() == -1) {
        
        final int targetObjectId = env.getVisibleObject().getObjectId();
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                _1034DisappearingAether.this.updateQuestStatus(player, qs);
              }
            }3000L);
      } 
    } 
    return false;
  }
}
