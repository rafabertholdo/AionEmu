package quest.ascension;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _2902DispatchtoAltgard extends QuestHandler {
  private static final int questId = 2902;

  public _2902DispatchtoAltgard() {
    super(Integer.valueOf(2902));
  }

  public void register() {
    this.qe.addQuestLvlUp(2902);
    this.qe.setNpcQuestData(204191).addOnTalkEvent(2902);
    this.qe.setNpcQuestData(203559).addOnTalkEvent(2902);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2902);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 204191:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              break;
            case 10000:
              if (var == 0) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                TeleportService.teleportTo(player, 220030000, player.getInstanceId(), 1748.0F, 1807.0F, 255.0F, 1000);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                return true;
              }
              break;
          }
        case 203559:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 1) {

                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
              }
              break;
          }
          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203559) {
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2902);
    boolean lvlCheck = QuestService.checkLevelRequirement(2902, player.getCommonData().getLevel());
    if (qs != null || !lvlCheck) {
      return false;
    }
    env.setQuestId(Integer.valueOf(2902));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}
