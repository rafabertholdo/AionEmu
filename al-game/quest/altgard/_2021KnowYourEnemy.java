package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;



















public class _2021KnowYourEnemy
  extends QuestHandler
{
  private static final int questId = 2021;
  
  public _2021KnowYourEnemy() {
    super(Integer.valueOf(2021));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2021);
    this.qe.setNpcQuestData(203669).addOnTalkEvent(2021);
    this.qe.setQuestEnterZone(ZoneName.BLACK_CLAW_OUTPOST_220030000).add(2021);
    this.qe.setNpcQuestData(700099).addOnKillEvent(2021);
    this.qe.setNpcQuestData(203557).addOnTalkEvent(2021);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2021);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {
        
        case 203669:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
              if (var == 2) {
                
                player.setTransformedModelId(0);
                PacketSendUtility.broadcastPacketAndReceive((VisibleObject)player, (AionServerPacket)new SM_TRANSFORM((Creature)player));
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              } 
              if (var == 6)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
              break;
            case 1012:
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 65));
              break;
            case 10000:
              if (var == 0) {
                
                player.setTransformedModelId(202501);
                PacketSendUtility.broadcastPacketAndReceive((VisibleObject)player, (AionServerPacket)new SM_TRANSFORM((Creature)player));
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                ThreadPoolManager.getInstance().schedule(new Runnable()
                    {
                      public void run()
                      {
                        if (player == null || player.getTransformedModelId() == 0)
                          return; 
                        player.setTransformedModelId(0);
                        PacketSendUtility.broadcastPacketAndReceive((VisibleObject)player, (AionServerPacket)new SM_TRANSFORM((Creature)player));
                      }
                    }300000L);
                return true;
              } 
              break;
            case 10001:
              if (var == 2) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
            case 10002:
              if (var == 6) {
                
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }  break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203557) {
        
        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        }
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2021);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (targetId == 700099 && var >= 3 && var < 6) {
      
      qs.setQuestVarById(0, var + 1);
      updateQuestStatus(player, qs);
      return true;
    } 
    return false;
  }


  
  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.BLACK_CLAW_OUTPOST_220030000)
      return false; 
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2021);
    if (qs == null)
      return false; 
    if (qs.getQuestVarById(0) == 1) {
      
      qs.setQuestVarById(0, 2);
      updateQuestStatus(player, qs);
      return true;
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2021);
    boolean lvlCheck = QuestService.checkLevelRequirement(2021, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2021KnowYourEnemy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
