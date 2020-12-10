package quest.ishalgen;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

public class _2132ANewSkill extends QuestHandler {
  private static final int questId = 2132;

  public _2132ANewSkill() {
    super(Integer.valueOf(2132));
  }

  public void register() {
    this.qe.addQuestLvlUp(2132);
    this.qe.setNpcQuestData(203527).addOnTalkEvent(2132);
    this.qe.setNpcQuestData(203528).addOnTalkEvent(2132);
    this.qe.setNpcQuestData(203529).addOnTalkEvent(2132);
    this.qe.setNpcQuestData(203530).addOnTalkEvent(2132);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    boolean lvlCheck = QuestService.checkLevelRequirement(2132, player.getCommonData().getLevel());
    if (!lvlCheck)
      return false;
    QuestState qs = player.getQuestStateList().getQuestState(2132);
    if (qs != null)
      return false;
    env.setQuestId(Integer.valueOf(2132));
    if (QuestService.startQuest(env, QuestStatus.START)) {

      qs = player.getQuestStateList().getQuestState(2132);
      qs.setStatus(QuestStatus.REWARD);
      switch (player.getCommonData().getPlayerClass()) {

        case WARRIOR:
          qs.setQuestVar(1);
          break;
        case SCOUT:
          qs.setQuestVar(2);
          break;
        case MAGE:
          qs.setQuestVar(3);
          break;
        case PRIEST:
          qs.setQuestVar(4);
          break;
      }
      updateQuestStatus(player, qs);
    }
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2132);
    if (qs == null || qs.getStatus() != QuestStatus.REWARD) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    PlayerClass playerClass = player.getCommonData().getPlayerClass();
    switch (targetId) {

      case 203527:
        if (playerClass == PlayerClass.WARRIOR) {

          if (env.getDialogId().intValue() == -1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
          if (env.getDialogId().intValue() == 1009) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }
          return defaultQuestEndDialog(env);
        }
        return false;
      case 203528:
        if (playerClass == PlayerClass.SCOUT) {

          if (env.getDialogId().intValue() == -1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          if (env.getDialogId().intValue() == 1009) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          }
          return defaultQuestEndDialog(env);
        }
        return false;
      case 203529:
        if (playerClass == PlayerClass.MAGE) {

          if (env.getDialogId().intValue() == -1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
          if (env.getDialogId().intValue() == 1009) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
          }
          return defaultQuestEndDialog(env);
        }
        return false;
      case 203530:
        if (playerClass == PlayerClass.PRIEST) {

          if (env.getDialogId().intValue() == -1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
          if (env.getDialogId().intValue() == 1009) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 8);
          }
          return defaultQuestEndDialog(env);
        }
        return false;
    }
    return false;
  }
}
