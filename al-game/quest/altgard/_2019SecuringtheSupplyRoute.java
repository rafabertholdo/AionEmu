package quest.altgard;

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
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _2019SecuringtheSupplyRoute extends QuestHandler {
  private static final int questId = 2019;

  public _2019SecuringtheSupplyRoute() {
    super(Integer.valueOf(2019));
  }

  public void register() {
    this.qe.addQuestLvlUp(2019);
    this.qe.setNpcQuestData(798033).addOnTalkEvent(2019);
    this.qe.setNpcQuestData(210492).addOnKillEvent(2019);
    this.qe.setNpcQuestData(210493).addOnKillEvent(2019);
    this.qe.setNpcQuestData(203673).addOnTalkEvent(2019);
    this.deletebleItems = new int[] { 182203024 };
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2019);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 798033:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
              if (var == 4)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              break;
            case 10000:
              if (var == 0) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
            case 10001:
              if (var == 4) {

                if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203024, 1))))
                  return true;
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
        case 203673:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 5)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
            case 1009:
              if (var == 5) {

                ItemService.removeItemFromInventoryByItemId(player, 182203024);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
              }
              break;
          }
          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203673)
        return defaultQuestEndDialog(env);
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2019);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    switch (targetId) {

      case 210492:
      case 210493:
        if (var >= 1 && var < 4) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2019);
    boolean lvlCheck = QuestService.checkLevelRequirement(2019, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}
