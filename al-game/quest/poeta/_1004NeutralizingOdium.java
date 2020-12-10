package quest.poeta;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;





















public class _1004NeutralizingOdium
  extends QuestHandler
{
  private static final int questId = 1004;
  
  public _1004NeutralizingOdium() {
    super(Integer.valueOf(1004));
  }


  
  public void register() {
    this.qe.addQuestLvlUp(1004);
    this.qe.setNpcQuestData(203082).addOnTalkEvent(1004);
    this.qe.setNpcQuestData(700030).addOnTalkEvent(1004);
    this.qe.setNpcQuestData(790001).addOnTalkEvent(1004);
    this.qe.setNpcQuestData(203067).addOnTalkEvent(1004);
  }


  
  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1004);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc)env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 203082) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
            if (var == 5)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
          case 1013:
            if (var == 0)
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 19)); 
            return false;
          case 10000:
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          case 10002:
            if (var == 5) {
              
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
            break;
        } 
      } else if ((targetId == 700030 && var == 1) || var == 4) {
        final int targetObjectId;
        switch (env.getDialogId().intValue()) {
          
          case -1:
            targetObjectId = env.getVisibleObject().getObjectId();
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    if (!player.isTargeting(targetObjectId)) {
                      return;
                    }
                    if (player.getTarget() == null || !(player.getTarget() instanceof Creature)) {
                      
                      PacketSendUtility.sendMessage(player, "Invalid target selected.");
                      return;
                    } 
                    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                    QuestState qs = player.getQuestStateList().getQuestState(1004);
                    if (qs.getQuestVarById(0) == 1)
                      ItemService.addItems(player, Collections.singletonList(new QuestItems(182200005, 1))); 
                    if (qs.getQuestVarById(0) == 4)
                      ItemService.decreaseItemCountByItemId(player, 182200006, 1L); 
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    _1004NeutralizingOdium.this.updateQuestStatus(player, qs);
                    PacketSendUtility.broadcastPacket(player.getTarget(), (AionServerPacket)new SM_EMOTION((Creature)player.getTarget(), EmotionType.EMOTE, 128, 0));
                  }
                }3000L);
            return false;
        } 
      
      } else if (targetId == 790001) {
        
        switch (env.getDialogId().intValue()) {
          
          case 25:
            if (var == 2)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
            if (var == 3)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
            if (var == 11)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694); 
          case 10001:
            if (var == 2) {
              
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
          case 10002:
            if (var == 11) {
              
              qs.setQuestVarById(0, 4);
              updateQuestStatus(player, qs);
              ItemService.decreaseItemCountByItemId(player, 182200005, 1L);
              ItemService.addItems(player, Collections.singletonList(new QuestItems(182200006, 1)));
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            } 
          
          case 33:
            if (QuestService.collectItemCheck(env, true)) {
              
              qs.setQuestVarById(0, 11);
              updateQuestStatus(player, qs);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
            } 
            
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
        } 

      
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203067)
        return defaultQuestEndDialog(env); 
    } 
    return false;
  }


  
  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1004);
    if (qs == null) {
      return false;
    }
    boolean lvlCheck = QuestService.checkLevelRequirement(1004, player.getCommonData().getLevel());
    if (!lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false; 
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1004NeutralizingOdium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
