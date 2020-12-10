package quest.heiron;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _1537FishOnTheLine extends QuestHandler {
  private static final int questId = 1537;

  public _1537FishOnTheLine() {
    super(Integer.valueOf(1537));
  }

  public void register() {
    this.qe.setNpcQuestData(204588).addOnQuestStart(1537);
    this.qe.setNpcQuestData(204588).addOnTalkEvent(1537);
    this.qe.setNpcQuestData(730189).addOnTalkEvent(1537);
    this.qe.setNpcQuestData(730190).addOnTalkEvent(1537);
    this.qe.setNpcQuestData(730191).addOnTalkEvent(1537);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    final QuestState qs = player.getQuestStateList().getQuestState(1537);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 204588)
      {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
    
    } else if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 730189:
          if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
            
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

                    
                    qs.setQuestVarById(0, 1);
                    _1537FishOnTheLine.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 



        
        case 730190:
          if (qs.getQuestVarById(0) == 1 && env.getDialogId().intValue() == -1) {
            
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

                    
                    qs.setQuestVarById(0, 2);
                    _1537FishOnTheLine.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 



        
        case 730191:
          if (qs.getQuestVarById(0) == 2 && env.getDialogId().intValue() == -1) {
            
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
                    
                    qs.setQuestVarById(0, 3);
                    qs.setStatus(QuestStatus.REWARD);
                    _1537FishOnTheLine.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 
          break;
      } 

    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 204588)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }
}
