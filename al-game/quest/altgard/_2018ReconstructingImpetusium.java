package quest.altgard;

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




















public class _2018ReconstructingImpetusium
  extends QuestHandler
{
  private static final int questId = 2018;
  
  public _2018ReconstructingImpetusium() {
    super(Integer.valueOf(2018));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(2018);
    this.qe.setNpcQuestData(203649).addOnTalkEvent(2018);
    this.qe.setNpcQuestData(210588).addOnKillEvent(2018);
    this.qe.setNpcQuestData(700097).addOnTalkEvent(2018);
    this.qe.setNpcQuestData(700098).addOnTalkEvent(2018);
    this.qe.setNpcQuestData(210752).addOnKillEvent(2018);
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2018);
    boolean lvlCheck = QuestService.checkLevelRequirement(2018, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2018);
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
        
        case 203649:
          switch (env.getDialogId().intValue()) {
            
            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
              if (var == 4)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
              if (var == 7)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
              break;
            case 1012:
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 22));
              break;
            case 10000:
            case 10001:
              if (var == 0 || var == 4) {
                
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              } 
            case 33:
              if (var == 7) {
                
                if (QuestService.collectItemCheck(env, true)) {
                  
                  qs.setStatus(QuestStatus.REWARD);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                } 
                
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
              }  break;
          } 
          break;
        case 700097:
          if (var == 5)
            return true; 
          break;
        case 700098:
          switch (env.getDialogId().intValue()) {
            
            case -1:
              env.setQuestId(Integer.valueOf(2018));
              if (var == 5 && QuestService.collectItemCheck(env, false)) {
                
                final int targetObjectId = env.getVisibleObject().getObjectId();
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

                        
                        QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 210752, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), true);
                        npc.getController().onDie(null);
                      }
                    }3000L);
                
                return true;
              } 
              break;
          } 
          break;
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203649)
      {
        return defaultQuestEndDialog(env);
      }
    } 
    return false;
  }


  
  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2018);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    
    int targetId = 0;
    int var = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    env.setQuestId(Integer.valueOf(2018));
    switch (targetId) {
      
      case 210588:
        var = qs.getQuestVarById(0);
        if (var < 4) {
          
          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
        } 
        break;
      case 210752:
        var = qs.getQuestVarById(0);
        if (var == 5 && QuestService.collectItemCheck(env, false)) {
          
          qs.setQuestVarById(0, 7);
          updateQuestStatus(player, qs);
        } 
        break;
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2018ReconstructingImpetusium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
