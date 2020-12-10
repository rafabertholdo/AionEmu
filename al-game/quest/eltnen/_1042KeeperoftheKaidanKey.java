package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
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

public class _1042KeeperoftheKaidanKey extends QuestHandler {
  private static final int questId = 1042;
  private static final int[] npc_ids = new int[] { 203989, 203901 };
  private static final int[] mob_ids = new int[] { 212029, 212033 };

  public _1042KeeperoftheKaidanKey() {
    super(Integer.valueOf(1042));
  }

  public void register() {
    this.qe.addQuestLvlUp(1042);
    for (int npc_id : npc_ids)
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1042);
    this.qe.setQuestItemIds(182201018).add(1042);
    for (int mob_id : mob_ids) {
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(1042);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1042);
    boolean lvlCheck = QuestService.checkLevelRequirement(1042, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    QuestState qs2 = player.getQuestStateList().getQuestState(1040);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1042);
    if (qs == null)
      return false;
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203901) {

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
    if (targetId == 203989) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        case 1012:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 185));
          break;
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
    } else if (targetId == 203901) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        case 33:
          if (QuestService.collectItemCheck(env, true)) {

            ItemService.decreaseItemCountByItemId(player, 182201018, 1L);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
      }
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    Player player = env.getPlayer();
    int id = item.getItemTemplate().getTemplateId();
    int itemObjId = item.getObjectId();
    if (id != 182201018)
      return false;
    QuestState qs = player.getQuestStateList().getQuestState(1042);
    PacketSendUtility.broadcastPacket(player,
        (AionServerPacket) new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
    SetQuestStatus2(player, env, qs);
    return true;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1042);
    if (qs == null || qs.getStatus() != QuestStatus.START)
      return false;
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    switch (targetId) {

      case 212029:
        if (var < 2) {

          SetQuestStatus2(player, env, qs);
          return true;
        }
        break;

      case 212033:
        if (var < 2) {

          SetQuestStatus2(player, env, qs);
          return true;
        }
        break;
    }
    return false;
  }

  private void SetQuestStatus2(Player player, QuestEnv env, QuestState qs) {
    qs.setQuestVarById(0, 2);
    updateQuestStatus(player, qs);
  }
}
