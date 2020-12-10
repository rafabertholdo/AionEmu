package quest.ascension;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ASCENSION_MORPH;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
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
import java.util.Collections;
import java.util.List;




















public class _2008Ascension
  extends QuestHandler
{
  private static final int questId = 2008;
  
  public _2008Ascension() {
    super(Integer.valueOf(2008));
  }


  
  public void register() {
    if (CustomConfig.ENABLE_SIMPLE_2NDCLASS)
      return; 
    this.qe.addQuestLvlUp(2008);
    this.qe.setNpcQuestData(203550).addOnTalkEvent(2008);
    this.qe.setNpcQuestData(790003).addOnTalkEvent(2008);
    this.qe.setNpcQuestData(790002).addOnTalkEvent(2008);
    this.qe.setNpcQuestData(203546).addOnTalkEvent(2008);
    this.qe.setNpcQuestData(205020).addOnTalkEvent(2008);
    this.qe.setNpcQuestData(205040).addOnKillEvent(2008);
    this.qe.setNpcQuestData(205041).addOnAttackEvent(2008);
    this.qe.setQuestMovieEndIds(152).add(2008);
    this.qe.addOnEnterWorld(2008);
    this.qe.addOnDie(2008);
    this.qe.addOnQuestFinish(2008);
    this.deletebleItems = new int[] { 182203009, 182203010, 182203011 };
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int instanceId = player.getInstanceId();
    QuestState qs = player.getQuestStateList().getQuestState(2008);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (targetId == 205040) {
      
      if (var >= 51 && var <= 53) {
        
        qs.setQuestVar(qs.getQuestVars().getQuestVars() + 1);
        updateQuestStatus(player, qs);
        return true;
      } 
      if (var == 54) {
        
        qs.setQuestVar(5);
        updateQuestStatus(player, qs);
        Npc mob = (Npc)QuestService.addNewSpawn(320010000, instanceId, 205041, 301.0F, 259.0F, 205.5F, (byte)0, true);
        
        mob.getGameStats().setStat(StatEnum.MAIN_HAND_POWER, mob.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER) / 3);
        mob.getAggroList().addDamage((Creature)player, 1000);
        return true;
      } 
    } 
    return false;
  }


  
  public boolean onAttackEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2008);
    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 5)
      return false; 
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (targetId != 205041)
      return false; 
    Npc npc = (Npc)env.getVisibleObject();
    if (npc.getLifeStats().getCurrentHp() < npc.getLifeStats().getMaxHp() / 2) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 152));
      npc.getController().onDelete();
    } 
    return false;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final int instanceId = player.getInstanceId();
    final QuestState qs = player.getQuestStateList().getQuestState(2008);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVars().getQuestVars();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203550) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
            if (var == 4)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
            if (var == 6)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
          case 2376:
            if (var == 4) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 57));
              ItemService.removeItemFromInventoryByItemId(player, 182203009);
              ItemService.removeItemFromInventoryByItemId(player, 182203010);
              ItemService.removeItemFromInventoryByItemId(player, 182203011);
              return false;
            } 
          case 10000:
            if (var == 0) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
          case 10004:
            if (var == 4) {
              
              qs.setQuestVar(99);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
              
              WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(320010000);
              InstanceService.registerPlayerWithInstance(newInstance, player);
              TeleportService.teleportTo(player, 320010000, newInstance.getInstanceId(), 457.65F, 426.8F, 230.4F, 0);
              return true;
            } 
          case 10005:
            if (var == 6) {
              
              PlayerClass playerClass = player.getCommonData().getPlayerClass();
              if (playerClass == PlayerClass.WARRIOR)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
              if (playerClass == PlayerClass.SCOUT)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
              if (playerClass == PlayerClass.MAGE)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
              if (playerClass == PlayerClass.PRIEST)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
            } 
          case 10006:
            if (var == 6)
              return setPlayerClass(env, qs, PlayerClass.GLADIATOR); 
          case 10007:
            if (var == 6)
              return setPlayerClass(env, qs, PlayerClass.TEMPLAR); 
          case 10008:
            if (var == 6)
              return setPlayerClass(env, qs, PlayerClass.ASSASSIN); 
          case 10009:
            if (var == 6)
              return setPlayerClass(env, qs, PlayerClass.RANGER); 
          case 10010:
            if (var == 6)
              return setPlayerClass(env, qs, PlayerClass.SORCERER); 
          case 10011:
            if (var == 6)
              return setPlayerClass(env, qs, PlayerClass.SPIRIT_MASTER); 
          case 10012:
            if (var == 6)
              return setPlayerClass(env, qs, PlayerClass.CHANTER); 
          case 10013:
            if (var == 6)
              return setPlayerClass(env, qs, PlayerClass.CLERIC); 
            break;
        } 
      } else if (targetId == 790003) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
          case 10001:
            if (var == 1) {
              
              if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203009, 1)))) {
                return true;
              }
              qs.setQuestVarById(0, 2);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            break;
        } 
      } else if (targetId == 790002) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 2)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
          case 10002:
            if (var == 2) {
              
              if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203010, 1))))
                return true; 
              qs.setQuestVarById(0, 3);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            break;
        } 
      } else if (targetId == 203546) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 3)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          case 10003:
            if (var == 3) {
              
              if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203011, 1)))) {
                return true;
              }
              qs.setQuestVarById(0, 4);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            break;
        } 
      } else if (targetId == 205020) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 99) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 3001, 0));
              qs.setQuestVar(50);
              updateQuestStatus(player, qs);
              ThreadPoolManager.getInstance().schedule(new Runnable()
                  {
                    public void run()
                    {
                      qs.setQuestVar(51);
                      _2008Ascension.this.updateQuestStatus(player, qs);
                      List<Npc> mobs = new ArrayList<Npc>();
                      mobs.add((Npc)QuestService.addNewSpawn(320010000, instanceId, 205040, 294.0F, 277.0F, 207.0F, (byte)0, true));
                      mobs.add((Npc)QuestService.addNewSpawn(320010000, instanceId, 205040, 305.0F, 279.0F, 206.5F, (byte)0, true));
                      mobs.add((Npc)QuestService.addNewSpawn(320010000, instanceId, 205040, 298.0F, 253.0F, 205.7F, (byte)0, true));
                      mobs.add((Npc)QuestService.addNewSpawn(320010000, instanceId, 205040, 306.0F, 251.0F, 206.0F, (byte)0, true));
                      for (Npc mob : mobs) {

                        
                        mob.getGameStats().setStat(StatEnum.MAIN_HAND_POWER, mob.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER) / 3);
                        mob.getGameStats().setStat(StatEnum.PHYSICAL_DEFENSE, 0);
                        mob.getAggroList().addDamage((Creature)player, 1000);
                      } 
                    }
                  }43000L);
              return true;
            } 
            return false;
        } 
        return false;
      }
    
    }
    else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203550)
      {
        return defaultQuestEndDialog(env);
      }
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2008);
    if (qs == null) {
      
      boolean lvlCheck = QuestService.checkLevelRequirement(2008, player.getCommonData().getLevel());
      if (!lvlCheck)
        return false; 
      env.setQuestId(Integer.valueOf(2008));
      QuestService.startQuest(env, QuestStatus.START);
      return true;
    } 
    return false;
  }


  
  public boolean onEnterWorldEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2008);
    if (qs != null && qs.getStatus() == QuestStatus.START) {
      
      int var = qs.getQuestVars().getQuestVars();
      if (var == 5 || (var >= 50 && var <= 55) || var == 99)
      {
        if (player.getWorldId() != 320010000) {
          
          qs.setQuestVar(4);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, new Object[] { DataManager.QUEST_DATA.getQuestById(2008).getName() }));
        }
        else {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ASCENSION_MORPH(1));
          return true;
        } 
      }
    } 
    return false;
  }


  
  public boolean onMovieEndEvent(QuestEnv env, int movieId) {
    if (movieId != 152)
      return false; 
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2008);
    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 5)
      return false; 
    int instanceId = player.getInstanceId();
    QuestService.addNewSpawn(320010000, instanceId, 203550, 301.93F, 274.26F, 205.7F, (byte)0, true);
    qs.setQuestVar(6);
    updateQuestStatus(player, qs);
    return true;
  }

  
  private boolean setPlayerClass(QuestEnv env, QuestState qs, PlayerClass playerClass) {
    Player player = env.getPlayer();
    player.getCommonData().setPlayerClass(playerClass);
    player.getCommonData().upgradePlayer();
    qs.setStatus(QuestStatus.REWARD);
    updateQuestStatus(player, qs);
    sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
    return true;
  }


  
  public boolean onDieEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2008);
    if (qs == null || qs.getStatus() != QuestStatus.START)
      return false; 
    if (qs.getStatus() != QuestStatus.START)
      return false; 
    int var = qs.getQuestVars().getQuestVars();
    if (var == 5 || (var >= 51 && var <= 53)) {
      
      qs.setQuestVar(4);
      updateQuestStatus(player, qs);
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, new Object[] { DataManager.QUEST_DATA.getQuestById(env.getQuestId().intValue()).getName() }));
    } 
    return false;
  }


  
  public boolean onQuestFinishEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2008);
    if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
      
      TeleportService.teleportTo(player, 220010000, 1, 385.0F, 1895.0F, 327.0F, (byte)20, 0);
      return true;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ascension\_2008Ascension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
