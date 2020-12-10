package quest.morheim;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;

public class _2393TheLoveOfAFather extends QuestHandler {
  private static final int questId = 2393;

  public _2393TheLoveOfAFather() {
    super(Integer.valueOf(2393));
  }

  public void register() {
    this.qe.setQuestItemIds(182204162).add(2393);
    this.qe.setNpcQuestData(204343).addOnQuestStart(2393);
    this.qe.setNpcQuestData(204343).addOnTalkEvent(2393);
  }

  public boolean onItemUseEvent(QuestEnv env, final Item item) {
     final Player player = env.getPlayer();
     final int id = item.getItemTemplate().getTemplateId();
     final int itemObjId = item.getObjectId();
     
     if (id != 182204162)
       return false; 
     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.Q2393))
       return false; 
     final QuestState qs = player.getQuestStateList().getQuestState(2393);
     if (qs == null)
       return false; 
     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
     ThreadPoolManager.getInstance().schedule(new Runnable()
         {
           public void run()
           {
             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
             ItemService.removeItemFromInventory(player, item);
             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204163, 1)));
             qs.setStatus(QuestStatus.REWARD);
             _2393TheLoveOfAFather.this.updateQuestStatus(player, qs);
           }
         }3000L);
     return false;
   }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(2393);
    if (targetId == 204343) {

      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        if (env.getDialogId().intValue() == 1002) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182204162, 1)))) {
            return defaultQuestStartDialog(env);
          }
          return true;
        }

        return defaultQuestStartDialog(env);
      }

      if (qs.getStatus() == QuestStatus.REWARD) {

        if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.REWARD)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        if (env.getDialogId().intValue() == 1009) {

          qs.setQuestVar(2);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
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
 * !\quest\morheim\_2393TheLoveOfAFather.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */