package quest.morheim;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
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
import java.util.Collections;

public class _2032GuardianSpirit extends QuestHandler {
  private static final int questId = 2032;
  private int itemId = 182204005;

  public _2032GuardianSpirit() {
    super(Integer.valueOf(2032));
  }

  public void register() {
    this.qe.addQuestLvlUp(2032);
    this.qe.setQuestItemIds(this.itemId).add(2032);
    this.qe.setNpcQuestData(204302).addOnTalkEvent(2032);
    this.qe.setNpcQuestData(204329).addOnTalkEvent(2032);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2032);
    boolean lvlCheck = QuestService.checkLevelRequirement(2032, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2032);

    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;

    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 204302:
          if (var == 0) {

            switch (env.getDialogId().intValue()) {

              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
              case 10000:
              case 10001:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }
            return defaultQuestStartDialog(env);
          }
          break;

        case 204329:
          switch (env.getDialogId().intValue()) {

            case 25:
              switch (var) {

                case 1:
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                case 2:
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
              }
            case 1353:
              if (var == 1)
                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 73));
              break;
            case 10000:
            case 10001:
            case 10003:
              switch (var) {

                case 1:
                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);
                  PacketSendUtility.sendPacket(player,
                      (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                  return true;
                case 2:
                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);
                  PacketSendUtility.sendPacket(player,
                      (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                  return true;
                case 3:
                  if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(this.itemId, 1)))) {
                    return true;
                  }
                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);
                  PacketSendUtility.sendPacket(player,
                      (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                  return true;
              }
            case 33:
              if (var == 2) {

                if (QuestService.collectItemCheck(env, true)) {

                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                }

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
              }
              break;
          }
          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 204329)
        return defaultQuestEndDialog(env);
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
     final Player player = env.getPlayer();
     final int id = item.getItemTemplate().getTemplateId();
     final int itemObjId = item.getObjectId();
     
     if (id != this.itemId) {
       return false;
     }
     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.EXECUTION_GROUND_OF_DELTRAS_220020000)) {
       return false;
     }
     final QuestState qs = player.getQuestStateList().getQuestState(2032);
     
     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
     ThreadPoolManager.getInstance().schedule(new Runnable()
         {
           public void run()
           {
             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 88));
             ItemService.removeItemFromInventoryByItemId(player, _2032GuardianSpirit.this.itemId);
             qs.setStatus(QuestStatus.REWARD);
             _2032GuardianSpirit.this.updateQuestStatus(player, qs);
           }
         }3000L);
     
     return true;
   }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\morheim\_2032GuardianSpirit.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
