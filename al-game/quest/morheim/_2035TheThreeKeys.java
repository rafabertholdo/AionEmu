package quest.morheim;

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

public class _2035TheThreeKeys extends QuestHandler {
  private static final int questId = 2035;
  private static final int[] npc_ids = new int[] { 204317, 204408, 204407 };

  public _2035TheThreeKeys() {
    super(Integer.valueOf(2035));
  }

  public void register() {
    this.qe.addQuestLvlUp(2035);
    this.qe.addOnEnterWorld(2035);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2035);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2035);
    boolean lvlCheck = QuestService.checkLevelRequirement(2035, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onEnterWorldEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2035);
    if (qs != null && qs.getStatus() == QuestStatus.START) {
      if (player.getWorldId() == 320050000 && qs.getQuestVarById(0) == 5) {

        qs.setQuestVar(6);
        updateQuestStatus(player, qs);
      }
    }
    return false;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2035);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 204317:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
            case 10000:
              if (var == 0) {

                qs.setQuestVarById(0, 4);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
        case 204408:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 4)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
              if (var == 6)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
            case 2376:
              PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 78));
              break;
            case 10004:
              if (var == 4) {
                ItemService.addItems(player, Collections.singletonList(new QuestItems(182204011, 1)));
                qs.setQuestVarById(0, 5);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
            case 33:
              if (var == 6) {

                if (QuestService.collectItemCheck(env, true)) {

                  ItemService.removeItemFromInventoryByItemId(player, 182204011);
                  qs.setStatus(QuestStatus.REWARD);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                }
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
              }
              break;
          }

          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 204407) {

        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
        }
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\morheim\_2035TheThreeKeys.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */