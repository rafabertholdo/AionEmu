package quest.heiron;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _1057CreatingaMonster extends QuestHandler {
  private static final int questId = 1057;
  private static final int[] npc_ids = new int[] { 204502, 204619, 700218, 700279, 204500 };

  public _1057CreatingaMonster() {
    super(Integer.valueOf(1057));
  }

  public void register() {
    this.qe.addQuestLvlUp(1057);
    this.qe.addOnEnterWorld(1057);
    this.qe.setNpcQuestData(700219).addOnKillEvent(1057);
    this.qe.setNpcQuestData(212211).addOnKillEvent(1057);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1057);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1057);
    boolean lvlCheck = QuestService.checkLevelRequirement(1057, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(1056);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(1057);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 204500) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
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
    if (targetId == 204502) {
      
      switch (env.getDialogId().intValue()) {
        
        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
        case 2036:
          if (var == 3)
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 190)); 
          return false;
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
          return false;
      } 
    
    } else if (targetId == 204619) {
      
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
          return false;
      } 
    
    } else if (targetId == 700218 && qs.getQuestVarById(0) == 2) {
      
      if (env.getDialogId().intValue() == -1)
      {
        final int targetObjectId = env.getVisibleObject().getObjectId();
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                _1057CreatingaMonster.this.sendQuestDialog(player, targetObjectId, 1693);
              }
            }3000L);
      }
      else if (qs.getQuestVarById(0) == 2 && env.getDialogId().intValue() == 10002)
      {
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
        ItemService.addItems(player, Collections.singletonList(new QuestItems(182201616, 1)));
        qs.setQuestVarById(0, 3);
        updateQuestStatus(player, qs);
        return true;
      }
    
    } else if (targetId == 700279 && qs.getQuestVarById(0) == 9) {
      
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
                qs.setStatus(QuestStatus.REWARD);
                _1057CreatingaMonster.this.updateQuestStatus(player, qs);
              }
            }3000L);
      } 
    } 
    return false;
  }

  public boolean onEnterWorldEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1057);
    if (qs != null && qs.getStatus() == QuestStatus.START) {
      if (player.getWorldId() == 310050000 && qs.getQuestVarById(0) == 4) {

        qs.setQuestVar(5);
        updateQuestStatus(player, qs);
      }
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1057);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId == 700219 && qs.getQuestVarById(0) < 8) {

      qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
      updateQuestStatus(player, qs);
    } else if (targetId == 212211 && qs.getQuestVarById(0) == 8) {

      qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
      updateQuestStatus(player, qs);
    }
    return false;
  }
}
