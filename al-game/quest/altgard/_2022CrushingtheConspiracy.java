package quest.altgard;

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
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _2022CrushingtheConspiracy extends QuestHandler {
  private static final int questId = 2022;

  public _2022CrushingtheConspiracy() {
    super(Integer.valueOf(2022));
  }

  public void register() {
    this.qe.addQuestLvlUp(2022);
    this.qe.setNpcQuestData(203557).addOnTalkEvent(2022);
    this.qe.setNpcQuestData(700140).addOnTalkEvent(2022);
    this.qe.setNpcQuestData(700142).addOnTalkEvent(2022);
    this.qe.setNpcQuestData(210753).addOnKillEvent(2022);
    this.qe.setNpcQuestData(700141).addOnTalkEvent(2022);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(2022);
    if (qs == null) {
      
      if (targetId == 203557)
      {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
    
    } else if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 203557:
          if (qs.getQuestVarById(0) == 0) {
            
            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            if (env.getDialogId().intValue() == 1009) {
              
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              
              return true;
            } 
          } 
          break;

        
        case 700140:
          if (qs.getQuestVarById(0) == 1) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            TeleportService.teleportTo(player, 320030000, 275.68F, 164.03F, 205.19F, 34);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;

        
        case 700142:
          if (qs.getQuestVarById(0) == 2) {
            
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), 700142, 3000, 0));
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, 700142), true);
                    QuestService.addNewSpawn(320030000, 1, 210753, 260.12F, 234.93F, 216.0F, (byte)90, true);
                  }
                }3000L);
            return true;
          } 
          break;


        
        case 700141:
          if (qs.getQuestVarById(0) == 4) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            TeleportService.teleportTo(player, 220030000, 2453.0F, 2553.2F, 316.3F, 26);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          } 
          break;
      } 
    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203557)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2022);
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

      case 210753:
        if (var >= 3 && var < 4) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2022);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    int[] quests = { 2200, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021 };
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
}
