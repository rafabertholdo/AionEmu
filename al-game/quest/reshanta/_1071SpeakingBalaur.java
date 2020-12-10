package quest.reshanta;

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
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _1071SpeakingBalaur extends QuestHandler {
  private static final int questId = 1071;

  public _1071SpeakingBalaur() {
    super(Integer.valueOf(1071));
  }

  public void register() {
    this.qe.addQuestLvlUp(1071);
    this.qe.setQuestItemIds(182202001).add(1071);
    this.qe.setNpcQuestData(278532).addOnTalkEvent(1071);
    this.qe.setNpcQuestData(798026).addOnTalkEvent(1071);
    this.qe.setNpcQuestData(798025).addOnTalkEvent(1071);
    this.qe.setNpcQuestData(279019).addOnTalkEvent(1071);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1071);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(1701);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1071);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 278532) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        return defaultQuestEndDialog(env);
      }
      return false;
    }
    if (qs.getStatus() != QuestStatus.START) {
      return false;
    }
    if (targetId == 278532) {

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

    } else if (targetId == 798026) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
          if (var == 6 || var == 8)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
        case 10004:
          if (var == 4) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            ItemService.decreaseItemCountByItemId(player, 182202002, 1L);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182202001, 1)));
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
        case 10006:
          if (var == 6 || var == 8) {

            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;

        case 10010:
          if (var == 1 && ItemService.decreaseKinah(player, 20000L)) {

            ItemService.addItems(player, Collections.singletonList(new QuestItems(182202001, 1)));
            qs.setQuestVar(7);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1355);
        case 10011:
          if (var == 1) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 798025) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        case 10002:
          if (var == 2) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 279019) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
          return false;
        case 10003:
          if (var == 3) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182202002, 1)));
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    Player player = env.getPlayer();
    int id = item.getItemTemplate().getTemplateId();
    int itemObjId = item.getObjectId();

    if (id != 182202001)
      return false;
    QuestState qs = player.getQuestStateList().getQuestState(1071);
    if (qs == null) {
      return false;
    }
    PacketSendUtility.broadcastPacket(player,
        (AionServerPacket) new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 1, 1, 0), true);
    ItemService.decreaseItemCountByItemId(player, 182202001, 1L);
    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
    updateQuestStatus(player, qs);
    return true;
  }
}
