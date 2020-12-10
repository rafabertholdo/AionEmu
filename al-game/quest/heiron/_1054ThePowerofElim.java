package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _1054ThePowerofElim extends QuestHandler {
  private static final int questId = 1054;
  private static final int[] npc_ids = new int[] { 730024, 204647, 730008, 730019 };

  public _1054ThePowerofElim() {
    super(Integer.valueOf(1054));
  }

  public void register() {
    this.qe.addQuestLvlUp(1054);
    this.qe.setQuestItemIds(182201608).add(1054);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1054);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1054);
    boolean lvlCheck = QuestService.checkLevelRequirement(1054, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(1500);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1054);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 204647) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {

      return false;
    }
    if (targetId == 730024) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        case 10000:
          if (var == 0) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

            return true;
          }
          return false;
      }

    } else if (targetId == 204647) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        case 33:
          if (QuestService.collectItemCheck(env, true)) {

            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
        case 2377:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 187));
          return false;
        case 10001:
          if (var == 1) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
        case 10004:
          if (var == 4) {

            ItemService.decreaseItemCountByItemId(player, 182201606, 1L);
            ItemService.decreaseItemCountByItemId(player, 182201607, 1L);
            qs.setQuestVarById(0, 5);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 730008) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        case 10002:
          if (var == 2) {

            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201606, 1)));
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 730019) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        case 10003:
          if (var == 3) {

            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201607, 1)));
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }
    }
    return false;
  }
}
