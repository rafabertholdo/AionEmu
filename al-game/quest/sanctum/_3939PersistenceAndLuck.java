package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _3939PersistenceAndLuck extends QuestHandler {
  private static final int questId = 3939;

  public _3939PersistenceAndLuck() {
    super(Integer.valueOf(3939));
  }

  public void register() {
    this.qe.setNpcQuestData(203701).addOnQuestStart(3939);
    this.qe.setNpcQuestData(203780).addOnTalkEvent(3939);
    this.qe.setNpcQuestData(203781).addOnTalkEvent(3939);
    this.qe.setNpcQuestData(203752).addOnTalkEvent(3939);
    this.qe.setNpcQuestData(203701).addOnTalkEvent(3939);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3939);

    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      if (targetId == 203701) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);

    if (qs.getStatus() == QuestStatus.START)

    {
      switch (targetId)

      {
        case 203780:
          if (var == 0) {
            switch (env.getDialogId().intValue()) {

              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);

              case 10000:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }

          }
          if (var == 2) {
            switch (env.getDialogId().intValue()) {

              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);

              case 33:
                if (player.getInventory().getItemCountByItemId(182206098) >= 20L) {

                  ItemService.removeItemFromInventoryByItemId(player, 182206098);
                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);

                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                }

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
            }

          }
          return false;
        case 203781:
          if (var == 1)
            switch (env.getDialogId().intValue()) {
              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              case 10001:
                if (ItemService.decreaseKinah(player, 3400000L)) {
                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);
                  PacketSendUtility.sendPacket(player,
                      (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                  return true;
                }
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
            }
          return false;
        case 203752:
          if (var == 3)
            switch (env.getDialogId().intValue()) {
              case 25:
                if (player.getInventory().getItemCountByItemId(186000080) >= 1L)
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
              case 10255:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
              case 1009:
                ItemService.removeItemFromInventoryByItemId(player, 186000080);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
            }
          return false;
      }
      return defaultQuestStartDialog(env);
    }
    if (qs.getStatus() == QuestStatus.REWARD)
      if (targetId == 203701)
        return defaultQuestEndDialog(env);
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\sanctum\_3939PersistenceAndLuck.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
