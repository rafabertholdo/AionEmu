package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _2200AltgardDuties extends QuestHandler {
  private static final int questId = 2200;

  public _2200AltgardDuties() {
    super(Integer.valueOf(2200));
  }

  public void register() {
    this.qe.setNpcQuestData(203557).addOnTalkEvent(2200);
    this.qe.setQuestEnterZone(ZoneName.ALTGARD_FORTRESS_220030000).add(2200);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2200);
    if (qs == null) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (targetId != 203557)
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

        int[] ids = { 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022 };
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
    if (zoneName != ZoneName.ALTGARD_FORTRESS_220030000)
      return false;
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2200);
    if (qs != null)
      return false;
    env.setQuestId(Integer.valueOf(2200));
    QuestService.startQuest(env, QuestStatus.START);
    return true;
  }
}
