package quest.morheim;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;

public class _2037TheProtectorofNepra extends QuestHandler {
  private static final int questId = 2037;
  private static final int[] npc_ids = new int[] { 204369, 204361, 278004 };

  public _2037TheProtectorofNepra() {
    super(Integer.valueOf(2037));
  }

  public void register() {
    this.qe.addQuestLvlUp(2037);
    this.qe.setNpcQuestData(212861).addOnKillEvent(2037);
    this.qe.setQuestEnterZone(ZoneName.ALTAR_OF_THE_BLACK_DRAGON_220020000).add(2037);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2037);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2037);
    boolean lvlCheck = QuestService.checkLevelRequirement(2037, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2037);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 204369:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
            case 1012:
              PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 80));
              break;
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

        case 204361:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              if (var == 3 && player.getInventory().getItemCountByItemId(182204015) == 1L)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
              if (var == 5)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
              if (var == 7)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
            case 10001:
              if (var == 1) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
            case 10003:
              if (var == 3) {

                ItemService.removeItemFromInventoryByItemId(player, 182204015);
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
            case 10005:
              if (var == 5) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
            case 10255:
              if (var == 7) {

                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }

        case 278004:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
            case 10002:
              if (var == 2) {

                ItemService.addItems(player, Collections.singletonList(new QuestItems(182204015, 1)));
                qs.setQuestVarById(0, var + 1);
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

      if (targetId == 204369) {

        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
        }
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2037);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START)
      return false;
    switch (targetId) {

      case 212861:
        if (var == 6) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }

  public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
    if (zoneName != ZoneName.ALTAR_OF_THE_BLACK_DRAGON_220020000)
      return false;
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2037);
    if (qs.getQuestVarById(0) == 4) {

      env.setQuestId(Integer.valueOf(2037));
      qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
      updateQuestStatus(player, qs);
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 81));
      return true;
    }
    return false;
  }
}
