package quest.sanctum;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
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
import java.util.ArrayList;
import java.util.List;

public class _1929ASliverofDarkness extends QuestHandler {
  private static final int questId = 1929;

  public _1929ASliverofDarkness() {
    super(Integer.valueOf(1929));
  }

  public void register() {
    this.qe.addQuestLvlUp(1929);
    this.qe.setNpcQuestData(203752).addOnTalkEvent(1929);
    this.qe.setNpcQuestData(203852).addOnTalkEvent(1929);
    this.qe.setNpcQuestData(203164).addOnTalkEvent(1929);
    this.qe.setNpcQuestData(205110).addOnTalkEvent(1929);
    this.qe.setNpcQuestData(700419).addOnTalkEvent(1929);
    this.qe.setNpcQuestData(205111).addOnTalkEvent(1929);
    this.qe.setQuestMovieEndIds(155).add(1929);
    this.qe.setNpcQuestData(212992).addOnKillEvent(1929);
    this.qe.setNpcQuestData(203701).addOnTalkEvent(1929);
    this.qe.setNpcQuestData(203711).addOnTalkEvent(1929);
    this.qe.addOnEnterWorld(1929);
    this.qe.addOnDie(1929);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final int instanceId = player.getInstanceId();
    final QuestState qs = player.getQuestStateList().getQuestState(1929);
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
        
        case 203752:
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
        case 203852:
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
        case 203164:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              if (var == 8)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
              break;
            case 10002:
              if (var == 2) {
                
                qs.setQuestVar(93);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(310070000);
                InstanceService.registerPlayerWithInstance(newInstance, player);
                TeleportService.teleportTo(player, 310070000, newInstance.getInstanceId(), 338.0F, 101.0F, 1191.0F, 0);
                return true;
              } 
              break;
            case 10006:
              if (var == 8) {
                
                removeStigma(player);
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
        case 205110:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 93)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
            case 10003:
              if (var == 93) {
                
                qs.setQuestVar(94);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 31001, 0));
                return true;
              } 
              break;
          } 
          break;
        case 700419:
          if (qs.getQuestVars().getQuestVars() == 94 && env.getDialogId().intValue() == -1) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    if (!player.isTargeting(targetObjectId))
                      return; 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);

                    
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 155));
                  }
                }3000L);
          } 
          break;
        
        case 205111:
          switch (env.getDialogId().intValue()) {
            
            case -1:
              if (var == 98) {
                
                int itemId = getStoneId(player);
                if (player.getEquipment().getEquippedItemsByItemId(itemId).size() != 0) {
                  
                  qs.setQuestVar(96);
                  updateQuestStatus(player, qs);
                } 
                return false;
              } 
              break;
            case 25:
              if (var == 98)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
              if (var == 96)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
              break;
            case 2546:
              if (var == 98) {
                
                int itemId = getStoneId(player);
                if (player.getInventory().getItemCountByItemId(itemId) > 0L) {
                  
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 1));
                  return true;
                } 
                List<QuestItems> items = new ArrayList<QuestItems>();
                items.add(new QuestItems(itemId, 1));
                items.add(new QuestItems(141000001, 60));
                if (ItemService.addItems(player, items))
                {
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 1));
                }
                return true;
              } 
              break;
            case 2720:
              if (var == 96) {
                
                Npc npc = (Npc)env.getVisibleObject();
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                npc.getController().delete();
                ThreadPoolManager.getInstance().schedule(new Runnable()
                    {
                      public void run()
                      {
                        QuestService.addNewSpawn(310070000, instanceId, 212992, 191.9F, 267.68F, 1374.0F, (byte)0, true);
                        qs.setQuestVar(97);
                        _1929ASliverofDarkness.this.updateQuestStatus(player, qs);
                      }
                    }5000L);
                return true;
              }  break;
          } 
          break;
        case 203701:
          if (var == 9)
          {
            switch (env.getDialogId().intValue()) {
              
              case 25:
                if (var == 9)
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
              case 10007:
                if (var == 9) {
                  
                  qs.setStatus(QuestStatus.REWARD);
                  updateQuestStatus(player, qs);
                  PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                  return true;
                } 
                break;
            } 
          }
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD && targetId == 203711) {
      
      if (env.getDialogId().intValue() == -1)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
      return defaultQuestEndDialog(env);
    } 
    
    return false;
  }

  public boolean onMovieEndEvent(QuestEnv env, int movieId) {
    if (movieId != 155)
      return false;
    Player player = env.getPlayer();
    int instanceId = player.getInstanceId();
    env.setQuestId(Integer.valueOf(1929));
    QuestState qs = player.getQuestStateList().getQuestState(1929);
    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 94)
      return false;
    QuestService.addNewSpawn(310070000, instanceId, 205111, 197.6F, 265.9F, 1374.0F, (byte) 0, true);
    qs.setQuestVar(98);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1929);
    if (qs != null)
      return false;
    boolean lvlCheck = QuestService.checkLevelRequirement(1929, player.getCommonData().getLevel());
    if (!lvlCheck)
      return false;
    env.setQuestId(Integer.valueOf(1929));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }

  public boolean onKillEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1929);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (targetId == 212992 && qs.getQuestVars().getQuestVars() == 97) {
      
      qs.setQuestVar(8);
      updateQuestStatus(player, qs);
      ThreadPoolManager.getInstance().schedule(new Runnable()
          {
            public void run()
            {
              TeleportService.teleportTo(player, 210030000, 1, 2315.9F, 1800.0F, 195.2F, 0);
            }
          }5000L);
      return true;
    } 
    return false;
  }

  public boolean onDieEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1929);
    if (qs == null || qs.getStatus() != QuestStatus.START)
      return false;
    int var = qs.getQuestVars().getQuestVars();
    if (var > 90) {

      removeStigma(player);
      qs.setQuestVar(2);
      updateQuestStatus(player, qs);
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1,
          new Object[] { DataManager.QUEST_DATA.getQuestById(1929).getName() }));
    }
    return false;
  }

  public boolean onEnterWorldEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1929);
    if (qs != null && qs.getStatus() == QuestStatus.START) {

      int var = qs.getQuestVars().getQuestVars();
      if (var > 90) {
        if (player.getWorldId() != 310070000) {

          removeStigma(player);
          qs.setQuestVar(2);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1,
              new Object[] { DataManager.QUEST_DATA.getQuestById(1929).getName() }));
        }
      }
    }
    return false;
  }

  private int getStoneId(Player player) {
    switch (player.getCommonData().getPlayerClass()) {

      case GLADIATOR:
        return 140000008;
      case TEMPLAR:
        return 140000027;
      case RANGER:
        return 140000047;
      case ASSASSIN:
        return 140000076;
      case SORCERER:
        return 140000131;
      case SPIRIT_MASTER:
        return 140000147;
      case CLERIC:
        return 140000098;
      case CHANTER:
        return 140000112;
    }
    return 0;
  }

  private void removeStigma(Player player) {
    int itemId = getStoneId(player);
    List<Item> items = player.getEquipment().getEquippedItemsByItemId(itemId);
    Equipment equipment = player.getEquipment();
    for (Item item : items) {
      equipment.unEquipItem(item.getObjectId(), 0);
    }
    ItemService.removeItemFromInventoryByItemId(player, itemId);
  }
}
