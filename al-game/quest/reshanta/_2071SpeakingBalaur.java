package quest.reshanta;

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
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _2071SpeakingBalaur extends QuestHandler {
  private static final int questId = 2071;
  private static final int[] npc_ids = new int[] { 278003, 278086, 278039, 279027, 204210 };

  public _2071SpeakingBalaur() {
    super(Integer.valueOf(2071));
  }

  public void register() {
    this.qe.addQuestLvlUp(2071);
    this.qe.setNpcQuestData(253610).addOnAttackEvent(2071);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2071);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2071);
    boolean lvlCheck = QuestService.checkLevelRequirement(2071, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(2701);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2071);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 278003) {

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
    if (targetId == 278003) {

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
    } else if (targetId == 278086) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        case 10001:
          if (var == 1) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          break;
      }
    } else if (targetId == 278039) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        case 10003:
          if (var == 3) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          break;
      }
    } else if (targetId == 279027) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
        case 3058:
          ItemService.removeItemFromInventoryByItemId(player, 182205501);
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 293));
          break;
        case 10004:
          if (var == 4) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
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
    } else if (targetId == 204210) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        case 10005:
          if (var == 5) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182205501, 1)));
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          break;
      }
    }
    return false;
  }

  public boolean onAttackEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2071);

    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 2) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (targetId != 253610) {
      return false;
    }
    Npc npc = (Npc) env.getVisibleObject();

    if (MathUtil.getDistance(1172.0F, 1959.0F, 1605.0F, npc.getX(), npc.getY(), npc.getZ()) > 15.0D) {
      return false;
    }
    if (npc.getLifeStats().getCurrentHp() < npc.getLifeStats().getMaxHp() / 3) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 289));
      npc.getController().onDie(null);
      npc.getController().onDespawn(true);
      qs.setQuestVarById(0, 3);
      updateQuestStatus(player, qs);
    } else {

      return false;
    }
    return true;
  }
}
