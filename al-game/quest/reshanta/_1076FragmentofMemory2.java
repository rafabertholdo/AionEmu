package quest.reshanta;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public class _1076FragmentofMemory2 extends QuestHandler {
  private static final int questId = 1076;
  private static final int[] npc_ids = new int[] { 278500, 203834, 203786, 203754, 203704 };

  public _1076FragmentofMemory2() {
    super(Integer.valueOf(1076));
  }

  public void register() {
    this.qe.addQuestLvlUp(1076);
    this.qe.setQuestItemIds(182202006).add(1076);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1076);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1076);
    boolean lvlCheck = QuestService.checkLevelRequirement(1076, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
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
    QuestState qs = player.getQuestStateList().getQuestState(1076);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203704) {

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
    if (targetId == 278500) {

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
          break;
      }
    } else if (targetId == 203834) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        case 1353:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 102));
          break;
        case 10001:
          if (var == 1) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
        case 10003:
          if (var == 3) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
        case 10005:
          if (var == 5) {

            qs.setQuestVarById(0, 6);
            updateQuestStatus(player, qs);
            ItemService.removeItemFromInventoryByItemId(player, 182202006);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          break;
      }
    } else if (targetId == 203786) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        case 33:
          if (QuestService.collectItemCheck(env, true)) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182202006, 1)));
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
      }

    } else if (targetId == 203754) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
        case 10255:
          if (var == 6) {

            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          break;
      }
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182202006) {
      return false;
    }
    final QuestState qs = player.getQuestStateList().getQuestState(1076);
    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 4) {
      return false;
    }
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 1000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 170));
            qs.setQuestVar(5);
            _1076FragmentofMemory2.this.updateQuestStatus(player, qs);
          }
        }1000L);
    return true;
  }
}
