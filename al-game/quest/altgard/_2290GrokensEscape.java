package quest.altgard;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _2290GrokensEscape extends QuestHandler {
  private static final int questId = 2290;

  public _2290GrokensEscape() {
    super(Integer.valueOf(2290));
  }

  public void register() {
    this.qe.setNpcQuestData(203608).addOnQuestStart(2290);
    this.qe.setNpcQuestData(203608).addOnTalkEvent(2290);
    this.qe.setNpcQuestData(700178).addOnTalkEvent(2290);
    this.qe.setNpcQuestData(203607).addOnTalkEvent(2290);
  }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    final QuestState qs = player.getQuestStateList().getQuestState(2290);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 203608) {
        
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
        if (env.getDialogId().intValue() == 1002) {
          
          if (QuestService.startQuest(env, QuestStatus.START)) {
            
            Npc npc = (Npc)env.getVisibleObject();
            npc.getMoveController().setNewDirection(1219.15F, 1212.0F, 247.37F);
            npc.getMoveController().schedule();
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1003);
          } 
        } else {
          
          return defaultQuestStartDialog(env);
        } 
      } 
    } else if (qs.getStatus() == QuestStatus.START) {
      
      switch (targetId) {

        
        case 700178:
          if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
            
            for (VisibleObject obj : player.getKnownList().getKnownObjects().values()) {
              
              if (!(obj instanceof Npc))
                continue; 
              if (((Npc)obj).getNpcId() != 203608)
                continue; 
              if (MathUtil.getDistance(player.getX(), player.getY(), player.getZ(), obj.getX(), obj.getY(), obj.getZ()) > 4.0D)
                return false; 
              ((Npc)obj).getController().onDie(null);
              ((Npc)obj).getController().onDespawn(false);
            } 
            final int targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 69));
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
                    _2290GrokensEscape.this.updateQuestStatus(player, qs);
                  }
                }3000L);
          } 
          break;

        
        case 203607:
          if (qs.getQuestVarById(0) == 3) {
            
            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
            if (env.getDialogId().intValue() == 1009) {
              
              ItemService.removeItemFromInventoryByItemId(player, 182203208);
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              return defaultQuestEndDialog(env);
            } 
            
            return defaultQuestEndDialog(env);
          } 
          break;
      } 
    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203607)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }
}
