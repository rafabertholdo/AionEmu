package quest.morheim;

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

public class _2039AlliesAmongEnemies extends QuestHandler {
  private static final int questId = 2039;
  private static final int[] npc_ids = new int[] { 204345, 204387, 204388, 204411, 204412, 204413 };

  public _2039AlliesAmongEnemies() {
    super(Integer.valueOf(2039));
  }

  public void register() {
    this.qe.addQuestLvlUp(2039);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2039);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2039);
    boolean lvlCheck = QuestService.checkLevelRequirement(2039, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2039);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 204345:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
            case 10000:
              if (var == 0) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
        case 204387:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
            case 1353:
              PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 84));
              break;
            case 10001:
              if (var == 1) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
            case 10255:
              if (var == 2) {

                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
        case 204411:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
            case 10002:
              if (var == 2) {

                qs.setQuestVarById(1, 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
        case 204412:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
            case 10003:
              if (var == 2) {

                qs.setQuestVarById(2, 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
        case 204413:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1864);
            case 10004:
              if (var == 2) {

                qs.setQuestVarById(3, 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 204388) {

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
 * !\quest\morheim\_2039AlliesAmongEnemies.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
