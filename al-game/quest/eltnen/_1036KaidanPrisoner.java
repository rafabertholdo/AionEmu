package quest.eltnen;

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

public class _1036KaidanPrisoner extends QuestHandler {
  private static final int questId = 1036;
  private static final int[] npc_ids = new int[] { 203904, 204045, 204003, 204004, 204020, 203901 };

  public _1036KaidanPrisoner() {
    super(Integer.valueOf(1036));
  }

  public void register() {
    this.qe.addQuestLvlUp(1036);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1036);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1036);
    boolean lvlCheck = QuestService.checkLevelRequirement(1036, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1036);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203901) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {

      return false;
    }
    if (targetId == 203904) {

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

    } else if (targetId == 204045) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        case 1354:
          if (var == 1)
            PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 32));
          break;
        case 10001:
          if (var == 1) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 204003) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
          if (var == 3 && QuestService.collectItemCheck(env, true)) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
          }
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
        case 10002:
          if (var == 2) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
        case 10003:
          if (var == 3) {

            PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 50));
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 204004) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        case 10004:
          if (var == 4) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201004, 1)));
            return true;
          }
          return false;
      }

    } else if (targetId == 204020) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        case 2717:
          ItemService.decreaseItemCountByItemId(player, 182201004, 1L);
        case 10004:
          if (var == 5) {

            qs.setQuestVarById(0, var + 1);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182201005, 1)));
            return true;
          }
          return false;
      }

    } else if (targetId == 203901) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
        case 1009:
          if (var == 6) {

            ItemService.decreaseItemCountByItemId(player, 182201005, 1L);
            qs.setStatus(QuestStatus.REWARD);
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }
          return false;
      }
    }
    return false;
  }
}
