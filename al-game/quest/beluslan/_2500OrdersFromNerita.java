package quest.beluslan;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _2500OrdersFromNerita extends QuestHandler {
  private static final int questId = 2500;

  public _2500OrdersFromNerita() {
    super(Integer.valueOf(2500));
  }

  public void register() {
    this.qe.setNpcQuestData(204702).addOnTalkEvent(2500);
    this.qe.setQuestEnterZone(ZoneName.BELUSLAN_FORTRESS_220040000).add(2500);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2500);
    boolean lvlCheck = QuestService.checkLevelRequirement(2500, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2500);
    if (qs == null) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (targetId != 204702)
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

        int[] ids = { 2051, 2052, 2053, 2054, 2055, 2056, 2057, 2058, 2059, 2060, 2061 };
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
    if (zoneName != ZoneName.BELUSLAN_FORTRESS_220040000)
      return false;
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2500);
    if (qs != null)
      return false;
    env.setQuestId(Integer.valueOf(2500));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}
