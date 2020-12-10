package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _1636AFluteForTheFixing extends QuestHandler {
  private static final int questId = 1636;

  public _1636AFluteForTheFixing() {
    super(Integer.valueOf(1636));
  }

  public void register() {
    this.qe.setNpcQuestData(204535).addOnQuestStart(1636);
    this.qe.setNpcQuestData(204535).addOnTalkEvent(1636);
    this.qe.setNpcQuestData(203792).addOnTalkEvent(1636);
    this.qe.setQuestItemIds(182201785).add(1636);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1636);

    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      if (targetId == 204535) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }

        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.START) {
      long itemCount1;
      long itemCount2;
      long itemCount3;
      long itemCount4;
      switch (targetId) {

        case 203792:
          switch (env.getDialogId().intValue()) {

            case 25:
              itemCount1 = player.getInventory().getItemCountByItemId(182201786);
              itemCount2 = player.getInventory().getItemCountByItemId(152020034);
              itemCount3 = player.getInventory().getItemCountByItemId(152020091);
              itemCount4 = player.getInventory().getItemCountByItemId(169400060);

              if (qs.getQuestVarById(0) == 0) {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
              }
              if (qs.getQuestVarById(0) == 1 && itemCount1 >= 1L && itemCount2 >= 1L && itemCount3 >= 1L
                  && itemCount4 >= 1L) {

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              }

              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);

            case 10000:
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));

              return true;

            case 33:
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 2);
              updateQuestStatus(player, qs);

              if (player.getInventory().getItemCountByItemId(182201785) == 0L) {
                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182201785, 1)))) {

                  return true;
                }
              }
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));

              return true;
          }

          break;
      }

    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 204535) {

        if (env.getDialogId().intValue() == 1009) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, final Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182201785)
    {
      return false;
    }
    
    final QuestState qs = player.getQuestStateList().getQuestState(1636);
    if (qs == null || qs.getStatus() != QuestStatus.START)
    {
      return false;
    }
    
    int var = qs.getQuestVars().getQuestVars();
    if (var != 3)
    {
      return false;
    }
    
    if (MathUtil.getDistance(182.0F, 2703.0F, 143.0F, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()) > 10.0D)
    {
      
      return false;
    }
    
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            
            ItemService.removeItemFromInventory(player, item);
            qs.setStatus(QuestStatus.REWARD);
            _1636AFluteForTheFixing.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }
}
