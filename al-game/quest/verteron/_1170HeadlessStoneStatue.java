package quest.verteron;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _1170HeadlessStoneStatue extends QuestHandler {
    private static final int questId = 1170;

    public _1170HeadlessStoneStatue() {
        super(Integer.valueOf(1170));
    }

    public void register() {
        this.qe.setNpcQuestData(730000).addOnQuestStart(1170);
        this.qe.setNpcQuestData(730000).addOnTalkEvent(1170);
        this.qe.setNpcQuestData(700033).addOnTalkEvent(1170);
        this.qe.setQuestMovieEndIds(16).add(1170);
    }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    final QuestState qs = player.getQuestStateList().getQuestState(1170);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      
      if (targetId == 730000)
      {
        env.setQuestId(Integer.valueOf(1170));
        QuestService.startQuest(env, QuestStatus.START);
        sendQuestDialog(player, 0, 1011);
        return false;
      }
    
    } else if (qs.getStatus() == QuestStatus.START) {
      
      if (targetId == 700033 && env.getDialogId().intValue() == -1) {
        
        final int targetObjectId = env.getVisibleObject().getObjectId();
        PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.SIT, 0, targetObjectId), true);
        
        ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                if (!player.isTargeting(targetObjectId))
                  return; 
                if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182200504, 1))))
                {
                  ((Npc)player.getTarget()).getController().onDespawn(true);
                  qs.setStatus(QuestStatus.REWARD);
                  _1170HeadlessStoneStatue.this.updateQuestStatus(player, qs);
                }
              
              }
            }3000L);
      } 
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 730000)
      {
        if (ItemService.removeItemFromInventoryByItemId(player, 182200504)) {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 16));
          return true;
        } 
      }
    } 
    return false;
  }

    public boolean onMovieEndEvent(QuestEnv env, int movieId) {
        if (movieId != 16)
            return false;
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1170);
        if (qs == null || qs.getStatus() != QuestStatus.REWARD)
            return false;
        int rewardExp = player.getRates().getQuestXpRate() * 8410;
        player.getCommonData().addExp(rewardExp);
        qs.setStatus(QuestStatus.COMPLETE);
        qs.setCompliteCount(1);
        updateQuestStatus(player, qs);
        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_QUEST_ACCEPTED(1170, QuestStatus.COMPLETE, 2));
        return true;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1170HeadlessStoneStatue.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */