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

public class _3969SexiestManAlive extends QuestHandler {
  private static final int questId = 3969;

  public _3969SexiestManAlive() {
    super(Integer.valueOf(3969));
  }

  public void register() {
    this.qe.setNpcQuestData(798390).addOnQuestStart(3969);
    this.qe.setNpcQuestData(798391).addOnTalkEvent(3969);
    this.qe.setNpcQuestData(798390).addOnTalkEvent(3969);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;

    QuestState qs2 = player.getQuestStateList().getQuestState(3968);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
      return false;
    }
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3969);

    if (targetId == 798390) {
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        if (env.getDialogId().intValue() == 1002) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206126, 1)))) {
            return defaultQuestStartDialog(env);
          }
          return true;
        }

        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);

    if (targetId == 798391) {

      if (qs.getStatus() == QuestStatus.START && var == 0) {
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10000) {

          if (ItemService.removeItemFromInventoryByItemId(player, 182206126)) {

            qs.setQuestVar(1);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          }

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 798390) {

      if (qs.getStatus() == QuestStatus.REWARD) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}
