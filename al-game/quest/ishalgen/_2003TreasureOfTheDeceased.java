package quest.ishalgen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _2003TreasureOfTheDeceased extends QuestHandler {
  private static final int questId = 2003;

  public _2003TreasureOfTheDeceased() {
    super(Integer.valueOf(2003));
  }

  public void register() {
    this.qe.addQuestLvlUp(2003);
    this.qe.setNpcQuestData(203539).addOnTalkEvent(2003);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2003);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (qs.getStatus() == QuestStatus.START) {

      if (targetId == 203539) {
        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          case 1012:
            PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 53));
            break;
          case 10000:
            if (var == 0) {

              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
          case 33:
            if (var == 1) {

              if (QuestService.collectItemCheck(env, true)) {

                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
              }

              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
            }
            break;
        }
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203539)
        return defaultQuestEndDialog(env);
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    boolean lvlCheck = QuestService.checkLevelRequirement(2003, player.getCommonData().getLevel());
    if (!lvlCheck)
      return false;
    QuestState qs = player.getQuestStateList().getQuestState(2003);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    QuestState qs2 = player.getQuestStateList().getQuestState(2100);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}
