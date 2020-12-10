package quest.verteron;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.InstanceService;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapType;

public class _1020SealingTheAbyssGate extends QuestHandler {
    private static final int questId = 1020;
    private static final int[] npcIds = new int[] { 203098, 700141, 700142, 700551 };

    public _1020SealingTheAbyssGate() {
        super(Integer.valueOf(1020));
    }

    public void register() {
        this.qe.addOnEnterWorld(1020);
        this.qe.addQuestLvlUp(1020);
        this.qe.addOnDie(1020);
        for (int npcId : npcIds) {
            this.qe.setNpcQuestData(npcId).addOnTalkEvent(1020);
        }
    }

    public boolean onLvlUpEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1020);
        boolean lvlCheck = QuestService.checkLevelRequirement(1020, player.getCommonData().getLevel());
        if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
            return false;
        int[] quests = { 1130, 1023, 1022, 1021, 1019, 1018, 1017, 1016, 1015, 1014, 1013, 1012, 1011 };
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
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(1020);
    if (qs == null)
      return false; 
    final int instanceId = player.getInstanceId();
    final int var = qs.getQuestVarById(0);
    int targetId = 0;
    
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203098) {
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
        return defaultQuestEndDialog(env);
      }
    
    } else if (qs.getStatus() != QuestStatus.START) {
      return false;
    }  switch (targetId) {
      
      case 203098:
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
          case 10000:
            if (var == 0) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 0);
            }  break;
        } 
        return false;
      
      case 700141:
        if (var == 1) {
          
          final int targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                
                public void run()
                {
                  qs.setQuestVarById(0, var + 1);
                  _1020SealingTheAbyssGate.this.updateQuestStatus(player, qs);
                  WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(310030000);
                  InstanceService.registerPlayerWithInstance(newInstance, player);
                  TeleportService.teleportTo(player, 310030000, newInstance.getInstanceId(), 270.5F, 174.3F, 204.3F, 0);
                }
              }3000L);
          return true;
        } 
        if (var == 3) {
          
          long itemCount = player.getInventory().getItemCountByItemId(182200024);
          if (itemCount >= 1L) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  
                  public void run()
                  {
                    ItemService.removeItemFromInventoryByItemId(player, 182200024);
                    qs.setStatus(QuestStatus.REWARD);
                    _1020SealingTheAbyssGate.this.updateQuestStatus(player, qs);
                    TeleportService.teleportTo(player, WorldMapType.VERTERON.getId(), 2684.308F, 1068.7382F, 199.375F, 0);
                  }
                }3000L);
            return true;
          } 
        } 
        return false;
      case 700551:
        if (var == 2) {
          
          long itemCount = player.getInventory().getItemCountByItemId(182200024);
          if (itemCount >= 1L) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  
                  public void run()
                  {
                    qs.setQuestVarById(0, var + 1);
                    _1020SealingTheAbyssGate.this.updateQuestStatus(player, qs);
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 153));
                  }
                }3000L);
            return true;
          } 
        } 
      case 700142:
        if (var == 2) {

          
          final int targetObjectId = env.getVisibleObject().getObjectId();
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                
                public void run()
                {
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                  QuestService.addNewSpawn(310030000, instanceId, 210753, 258.89917F, 237.20166F, 217.06035F, (byte)0, true);
                }
              }3000L);
          return true;
        } 
        break;
    } 
    return false;
  }

    public boolean onDieEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1020);
        if (qs == null || qs.getStatus() != QuestStatus.START)
            return false;
        int var = qs.getQuestVars().getQuestVars();
        if (var == 2 || var == 3) {

            qs.setQuestVar(1);
            ItemService.decreaseItemCountByItemId(player, 182200024, 1L);
            updateQuestStatus(player, qs);
        }

        return false;
    }

    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1020);
        if (qs == null) {
            return false;
        }
        if (qs.getStatus() == QuestStatus.START) {

            int var = qs.getQuestVars().getQuestVars();
            if (var == 2 || var == 3) {
                if (player.getWorldId() != 310030000) {
                    qs.setQuestVar(1);
                    ItemService.decreaseItemCountByItemId(player, 182200024, 1L);
                    updateQuestStatus(player, qs);
                }

            }
        } else if (qs.getStatus() == QuestStatus.LOCKED && player.getCommonData().getLevel() > 15) {

            int[] quests = { 1130, 1023, 1022, 1021, 1019, 1018, 1017, 1016, 1015, 1014, 1013, 1012, 1011 };
            for (int id : quests) {

                QuestState qs2 = player.getQuestStateList().getQuestState(id);
                if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
                    return false;
            }
            qs.setStatus(QuestStatus.START);
            updateQuestStatus(player, qs);
            return true;
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1020SealingTheAbyssGate.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */