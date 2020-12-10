package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _3938WellRounded extends QuestHandler {
  private static final int questId = 3938;

  public _3938WellRounded() {
    super(Integer.valueOf(3938));
  }

  public void register() {
    this.qe.setNpcQuestData(203701).addOnQuestStart(3938);
    this.qe.setNpcQuestData(203788).addOnTalkEvent(3938);
    this.qe.setNpcQuestData(203792).addOnTalkEvent(3938);
    this.qe.setNpcQuestData(203790).addOnTalkEvent(3938);
    this.qe.setNpcQuestData(203793).addOnTalkEvent(3938);
    this.qe.setNpcQuestData(203784).addOnTalkEvent(3938);
    this.qe.setNpcQuestData(203786).addOnTalkEvent(3938);
    this.qe.setNpcQuestData(798316).addOnTalkEvent(3938);
    this.qe.setNpcQuestData(203752).addOnTalkEvent(3938);
    this.qe.setNpcQuestData(203701).addOnTalkEvent(3938);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3938);

    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      if (targetId == 203701) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);

    if (qs.getStatus() == QuestStatus.START)

    {
      switch (targetId)

      {
        case 203701:
          if (var == 0)

          {
            switch (env.getDialogId().intValue()) {

              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);

              case 10000:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;

              case 10001:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;

              case 10002:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;

              case 10003:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;

              case 10004:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;

              case 10005:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }

            return false;
          }
        case 203788:
          if (var == 1)
            switch (env.getDialogId().intValue()) {
              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              case 10006:
                if (player.getInventory().getItemCountByItemId(152201596) == 0L)
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(152201596, 1))))
                    return true;
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }
          return false;
        case 203792:
          if (var == 2)
            switch (env.getDialogId().intValue()) {
              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
              case 10006:
                if (player.getInventory().getItemCountByItemId(152201639) == 0L)
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(152201639, 1))))
                    return true;
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }
          return false;
        case 203790:
          if (var == 3)
            switch (env.getDialogId().intValue()) {
              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
              case 10006:
                if (player.getInventory().getItemCountByItemId(152201615) == 0L)
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(152201615, 1))))
                    return true;
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }
          return false;
        case 203793:
          if (var == 4)
            switch (env.getDialogId().intValue()) {
              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
              case 10006:
                if (player.getInventory().getItemCountByItemId(152201632) == 0L)
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(152201632, 1))))
                    return true;
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }
          return false;
        case 203784:
          if (var == 5)
            switch (env.getDialogId().intValue()) {
              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
              case 10006:
                if (player.getInventory().getItemCountByItemId(152201644) == 0L)
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(152201644, 1))))
                    return true;
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }
          return false;
        case 203786:
          if (var == 6)
            switch (env.getDialogId().intValue()) {
              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
              case 10006:
                if (player.getInventory().getItemCountByItemId(152201643) == 0L)
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(152201643, 1))))
                    return true;
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }
          return false;
        case 798316:
          if (var == 7)
            switch (env.getDialogId().intValue()) {
              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398);
              case 33:
                if (player.getInventory().getItemCountByItemId(186000077) >= 1L) {
                  ItemService.removeItemFromInventoryByItemId(player, 186000077);
                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                }
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
            }
          return false;
        case 203752:
          if (var == 8)
            switch (env.getDialogId().intValue()) {
              case 25:
                if (player.getInventory().getItemCountByItemId(186000081) >= 1L)
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3825);
              case 10255:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
              case 1009:
                ItemService.removeItemFromInventoryByItemId(player, 186000081);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
            }
          return false;
      }
      return defaultQuestStartDialog(env);
    }
    if (qs.getStatus() == QuestStatus.REWARD)
      if (targetId == 203701)
        return defaultQuestEndDialog(env);
    return false;
  }
}
