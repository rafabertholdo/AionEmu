package quest.reshanta;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _2701TheGovernorsSummons extends QuestHandler {
  private static final int questId = 2701;

  public _2701TheGovernorsSummons() {
    super(Integer.valueOf(2701));
  }

  public void register() {
    this.qe.setNpcQuestData(278001).addOnTalkEvent(2701);
    this.qe.setQuestEnterZone(ZoneName.RUSSET_PLAZA_400010000).add(2701);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2701);
    boolean lvlCheck = QuestService.checkLevelRequirement(2701, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2701);
    if (qs == null) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (targetId != 278001)
      return false;
    if (qs.getStatus() == QuestStatus.START) {

      if (env.getDialogId().intValue() == 25)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
      if (env.getDialogId().intValue() == 1009) {

        qs.setStatus(QuestStatus.REWARD);
        qs.setQuestVarById(0, 1);
        updateQuestStatus(player, qs);
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
      }
      return false;
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (env.getDialogId().intValue() == 17) {

        int[] ids = { 2071, 2072, 2073, 2074, 2075, 2076 };
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
    if (zoneName != ZoneName.RUSSET_PLAZA_400010000)
      return false;
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2701);
    if (qs != null)
      return false;
    env.setQuestId(Integer.valueOf(2701));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}
