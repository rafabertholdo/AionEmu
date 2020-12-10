package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _1300OrdersfromTelemachus extends QuestHandler {
  private static final int questId = 1300;

  public _1300OrdersfromTelemachus() {
    super(Integer.valueOf(1300));
  }

  public void register() {
    this.qe.setNpcQuestData(203901).addOnTalkEvent(1300);
    this.qe.setQuestEnterZone(ZoneName.ELTNEN_FORTRESS).add(1300);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1300);
    boolean lvlCheck = QuestService.checkLevelRequirement(1300, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1300);
    if (qs == null) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (targetId != 203901)
      return false;
    if (qs.getStatus() == QuestStatus.START) {

      if (env.getDialogId().intValue() == 25) {

        qs.setQuestVar(1);
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
      }

      return defaultQuestStartDialog(env);
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (env.getDialogId().intValue() == 17) {

        int[] ids = { 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039, 1040, 1041, 1042, 1043 };
        for (int id : ids) {
          QuestService.startQuest(
              new QuestEnv(env.getVisibleObject(), env.getPlayer(), Integer.valueOf(id), env.getDialogId()),
              QuestStatus.LOCKED);
        }
      }
      return defaultQuestEndDialog(env);
    }
    return false;
  }

  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.ELTNEN_FORTRESS)
      return false;
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1300);
    if (qs != null)
      return false;
    env.setQuestId(Integer.valueOf(1300));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}
