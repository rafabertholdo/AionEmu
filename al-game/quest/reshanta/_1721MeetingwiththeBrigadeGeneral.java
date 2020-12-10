package quest.reshanta;

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

public class _1721MeetingwiththeBrigadeGeneral extends QuestHandler {
  private static final int questId = 1721;

  public _1721MeetingwiththeBrigadeGeneral() {
    super(Integer.valueOf(1721));
  }

  public void register() {
    this.qe.setNpcQuestData(278501).addOnQuestStart(1721);
    this.qe.setNpcQuestData(278501).addOnTalkEvent(1721);
    this.qe.setNpcQuestData(278503).addOnTalkEvent(1721);
    this.qe.setNpcQuestData(278502).addOnTalkEvent(1721);
    this.qe.setNpcQuestData(278518).addOnTalkEvent(1721);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1721);
    if (targetId == 278501) {

      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        if (env.getDialogId().intValue() == 1002) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182202151, 1)))) {
            return defaultQuestStartDialog(env);
          }
          return true;
        }

        return defaultQuestStartDialog(env);
      }
      if (qs.getStatus() == QuestStatus.START) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        }
      }
    } else if (targetId == 278503) {

      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10000) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 278502) {

      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        if (env.getDialogId().intValue() == 10001) {

          qs.setQuestVar(2);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          ItemService.removeItemFromInventoryByItemId(player, 182202151);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 278518) {

      if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}
