package quest.morheim;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;

public class _2031PetrifyingElim extends QuestHandler {
  private static final int questId = 2031;

  public _2031PetrifyingElim() {
    super(Integer.valueOf(2031));
  }

  public void register() {
    this.qe.setQuestItemIds(182204001).add(2031);
    this.qe.setNpcQuestData(204304).addOnTalkEvent(2031);
    this.qe.setNpcQuestData(730038).addOnTalkEvent(2031);
    this.qe.addQuestLvlUp(2031);
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
     final Player player = env.getPlayer();
     final int id = item.getItemTemplate().getTemplateId();
     final int itemObjId = item.getObjectId();
     
     if (id != 182204001) {
       return false;
     }
     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.HILL_OF_BELEMU_220020000)) {
       return false;
     }
     final QuestState qs = player.getQuestStateList().getQuestState(2031);
     
     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
     ThreadPoolManager.getInstance().schedule(new Runnable()
         {
           public void run()
           {
             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 72));
             ItemService.removeItemFromInventoryByItemId(player, 182204001);
             qs.setStatus(QuestStatus.REWARD);
             _2031PetrifyingElim.this.updateQuestStatus(player, qs);
           }
         }3000L);
     
     return false;
   }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2031);

    boolean lvlCheck = QuestService.checkLevelRequirement(2031, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);

    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;

    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    QuestState qs = player.getQuestStateList().getQuestState(2031);

    if (qs == null) {
      return false;
    }
    if (targetId == 204304) {

      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        if (env.getDialogId().intValue() == 10000) {

          qs.setQuestVar(1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 730038) {

      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 1353) {
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 71));
        } else {
          if (env.getDialogId().intValue() == 10001) {

            qs.setQuestVar(2);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }

          return defaultQuestStartDialog(env);
        }

      }
      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        if (env.getDialogId().intValue() == 33) {

          if (player.getInventory().getItemCountByItemId(182204001) > 0L) {

            qs.setQuestVar(3);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
        }

        return defaultQuestStartDialog(env);
      }

      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        if (env.getDialogId().intValue() == 10003) {

          qs.setQuestVar(4);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }

        return defaultQuestStartDialog(env);
      }
      if (qs.getStatus() == QuestStatus.REWARD) {
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\morheim\_2031PetrifyingElim.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
