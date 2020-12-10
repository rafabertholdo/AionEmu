package quest.poeta;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _1123WheresTutty extends QuestHandler {
  private static final int questId = 1123;

  public _1123WheresTutty() {
    super(Integer.valueOf(1123));
  }

  public void register() {
    this.qe.setNpcQuestData(790001).addOnTalkEvent(1123);
    this.qe.setNpcQuestData(790001).addOnQuestStart(1123);
    this.qe.setQuestEnterZone(ZoneName.Q1123).add(1123);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1123);
    if (targetId == 790001) {

      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
      if (qs.getStatus() == QuestStatus.REWARD) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.Q1123)
      return false;
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1123);
    if (qs == null || qs.getStatus() != QuestStatus.START)
      return false;
    env.setQuestId(Integer.valueOf(1123));
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 11));
    qs.setStatus(QuestStatus.REWARD);
    updateQuestStatus(player, qs);
    return true;
  }
}
