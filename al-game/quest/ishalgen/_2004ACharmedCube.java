package quest.ishalgen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _2004ACharmedCube extends QuestHandler {
  private static final int questId = 2004;

  public _2004ACharmedCube() {
    super(Integer.valueOf(2004));
  }

  public void register() {
    this.qe.addQuestLvlUp(2004);
    this.qe.setNpcQuestData(203539).addOnTalkEvent(2004);
    this.qe.setNpcQuestData(700047).addOnTalkEvent(2004);
    this.qe.setNpcQuestData(203550).addOnTalkEvent(2004);
    this.qe.setNpcQuestData(210402).addOnKillEvent(2004);
    this.qe.setNpcQuestData(210403).addOnKillEvent(2004);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2004);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (qs.getStatus() == QuestStatus.START) {
      final int targetObjectId;
      switch (targetId) {

        
        case 203539:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
              break;
            case 10000:
            case 10001:
              if (var == 0 || var == 1) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              break;
            case 33:
              if (var == 1) {
                
                if (player.getInventory().getItemCountByItemId(182203005) > 0L)
                {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
                }
                
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
              } 
              break;
          } 
          return false;

        
        case 700047:
          targetObjectId = env.getVisibleObject().getObjectId();
          if (env.getDialogId().intValue() == -1)
          {
            if (var == 1) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
              
              PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
              
              ThreadPoolManager.getInstance().schedule(new Runnable()
                  {
                    public void run()
                    {
                      Npc npc = (Npc)player.getTarget();
                      if (!player.isTargeting(targetObjectId))
                        return; 
                      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                      
                      PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                      
                      QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 211755, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), true);
                      npc.getController().onDie(null);
                    }
                  }3000L);
            } 
          }
          return false;

        
        case 203550:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              if (var == 6)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
              break;
            case 10002:
              if (var == 2) {
                
                ItemService.removeItemFromInventoryByItemId(player, 182203005);
                qs.setQuestVarById(0, 3);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              break;
            case 10003:
              if (var == 6) {
                
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203539) {
        
        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        }
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2004);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START)
      return false;
    switch (targetId) {

      case 210402:
      case 210403:
        if (var >= 3 && var < 8) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        if (var == 8) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2004);
    boolean lvlCheck = QuestService.checkLevelRequirement(2004, player.getCommonData().getLevel());
    if (!lvlCheck || qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}
