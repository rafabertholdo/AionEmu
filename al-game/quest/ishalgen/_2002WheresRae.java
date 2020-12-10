package quest.ishalgen;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
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
import com.aionemu.gameserver.services.InstanceService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;

















public class _2002WheresRae
  extends QuestHandler
{
  private static final int questId = 2002;
  private static final int[] npc_ids = new int[] { 203519, 203534, 203553, 700045, 203516, 205020, 203537 };

  
  public _2002WheresRae() {
    super(Integer.valueOf(2002));
  }

  
  public void register() {
    this.qe.addQuestLvlUp(2002);
    this.qe.setNpcQuestData(210377).addOnKillEvent(2002);
    this.qe.setNpcQuestData(210378).addOnKillEvent(2002);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2002);
    }
  }

  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    final QuestState qs = player.getQuestStateList().getQuestState(2002);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 203519:
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
        
        case 203534:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            case 1353:
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 52));
              break;
            case 10001:
              if (var == 1) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              break;
          } 
        
        case 790002:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              if (var == 10)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
              if (var == 11)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
              if (var == 12)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2462); 
              if (var == 13)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
            case 10002:
            case 10003:
            case 10005:
              if (var == 2 || var == 10) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              if (var == 13) {
                
                qs.setQuestVarById(0, 14);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
              break;
            case 10004:
              if (var == 12) {
                
                qs.setQuestVarById(0, 99);
                updateQuestStatus(player, qs);
                WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(320010000);
                InstanceService.registerPlayerWithInstance(newInstance, player);
                TeleportService.teleportTo(player, 320010000, newInstance.getInstanceId(), 457.65F, 426.8F, 230.4F, 75);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
            case 33:
              if (var == 11) {
                
                if (QuestService.collectItemCheck(env, true)) {
                  
                  qs.setQuestVarById(0, 12);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);
                } 
                
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2376);
              } 
              break;
          } 
          
          break;
        case 205020:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 3001, 0));
              ThreadPoolManager.getInstance().schedule(new Runnable()
                  {
                    public void run()
                    {
                      qs.setQuestVar(13);
                      _2002WheresRae.this.updateQuestStatus(player, qs);
                      TeleportService.teleportTo(player, 220010000, 1, 940.15F, 2295.64F, 265.7F, 43);
                    }
                  }38000L);
              return true;
          } 
          return false;

        
        case 700045:
          if (var == 11 && env.getDialogId().intValue() == -1) {
            
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    SkillEngine.getInstance().getSkill((Creature)player, 8343, 1, (VisibleObject)player).useSkill();
                  }
                }3000L);
          } 
          return true;
        case 203537:
          if (var == 14 && env.getDialogId().intValue() == -1) {
            
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            Npc npc = (Npc)env.getVisibleObject();
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 203553, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), true);
            npc.getController().onDie(null);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 256));
            return true;
          } 
          break;
        case 203553:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 15)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
            case 10006:
              if (var == 15) {
                
                env.getVisibleObject().getController().delete();
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203516) {
        
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
        if (env.getDialogId().intValue() == 10007) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2002);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START)
      return false; 
    switch (targetId) {
      
      case 210377:
      case 210378:
        if (var >= 3 && var < 10) {
          
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return true;
        }  break;
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2002);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2002WheresRae.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
