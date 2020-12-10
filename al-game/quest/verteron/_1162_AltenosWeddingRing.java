package quest.verteron;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _1162_AltenosWeddingRing extends QuestHandler {
    private static final int questId = 1162;

    public _1162_AltenosWeddingRing() {
        super(Integer.valueOf(1162));
    }

    public void register() {
        this.qe.setNpcQuestData(203095).addOnQuestStart(1162);
        this.qe.setNpcQuestData(203095).addOnTalkEvent(1162);
        this.qe.setNpcQuestData(203093).addOnTalkEvent(1162);
        this.qe.setNpcQuestData(700005).addOnTalkEvent(1162);
    }

  public boolean onDialogEvent(QuestEnv env) {
    final Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
    QuestState qs = player.getQuestStateList().getQuestState(1162);
    
    if (qs == null || qs.getStatus() == QuestStatus.NONE)
    {
      if (targetId == 203095) {
        
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      } 
    }
    
    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.START) {
      final int targetObjectId;
      switch (targetId) {

        
        case 700005:
          switch (env.getDialogId().intValue()) {

            
            case -1:
              if (player.getInventory().getItemCountByItemId(182200563) == 0L)
              {
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200563, 1))))
                {
                  
                  return true;
                }
              }
              
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
                      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
                      
                      PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
                      
                      QuestState qs = player.getQuestStateList().getQuestState(1162);
                      qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                      _1162_AltenosWeddingRing.this.updateQuestStatus(player, qs);
                      PacketSendUtility.broadcastPacket(player.getTarget(), (AionServerPacket)new SM_EMOTION((Creature)player.getTarget(), EmotionType.DIE, 128, 0));
                    }
                  }3000L);
              
              return true;
          } 
        
        
        
        case 203093:
        case 203095:
          if (qs.getQuestVarById(0) == 1) {
            
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            ItemService.removeItemFromInventoryByItemId(player, 182200563);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            
            return true;
          } 
          
          return false;
      } 
    
    } else if (qs.getStatus() == QuestStatus.REWARD) {
      
      if (targetId == 203095) {
        
        if (env.getDialogId().intValue() == 1009) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        return defaultQuestEndDialog(env);
      } 
    } 
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\verteron\_1162_AltenosWeddingRing.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */