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

public class _2033DestroyingtheCurse extends QuestHandler {
  private static final int questId = 2033;
  private int itemId = 182204007;

  public _2033DestroyingtheCurse() {
    super(Integer.valueOf(2033));
  }

  public void register() {
    this.qe.addQuestLvlUp(2033);
    this.qe.setQuestItemIds(this.itemId).add(2033);
    this.qe.setNpcQuestData(204391).addOnTalkEvent(2033);
    this.qe.setNpcQuestData(790020).addOnTalkEvent(2033);
    this.qe.setNpcQuestData(204393).addOnTalkEvent(2033);
    this.qe.setNpcQuestData(204394).addOnTalkEvent(2033);
    this.qe.setNpcQuestData(204395).addOnTalkEvent(2033);
    this.qe.setNpcQuestData(204396).addOnTalkEvent(2033);
    this.qe.setNpcQuestData(204397).addOnTalkEvent(2033);
    this.qe.setNpcQuestData(204398).addOnTalkEvent(2033);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2033);
    boolean lvlCheck = QuestService.checkLevelRequirement(2033, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2033);

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

        case 204391:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
              break;
            case 10000:
            case 10001:
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
        case 790020:
          switch (env.getDialogId().intValue()) {

            case 25:
              switch (var) {

                case 1:
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                case 2:
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
              }
            case 10000:
            case 10001:
            case 10003:
              if (var == 1) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              if (var == 3) {

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
        case 204393:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 4)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
            case 2376:
              if (var == 4)
                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 74));
              break;
            case 10000:
            case 10001:
            case 10004:
              if (var == 4) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
        case 204394:
        case 204395:
        case 204396:
        case 204397:
        case 204398:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 5)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
            case 10000:
            case 10001:
            case 10005:
              if (var == 5) {

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

      if (targetId == 790020) {
        return defaultQuestEndDialog(env);
      }
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
     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.Q2033)) {
       return false;
     }
     final QuestState qs = player.getQuestStateList().getQuestState(2033);
     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
     
     if (qs.getQuestVarById(0) == 6)
     {
       ThreadPoolManager.getInstance().schedule(new Runnable()
           {
             public void run()
             {
               PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 75));
               ItemService.removeItemFromInventoryByItemId(player, _2033DestroyingtheCurse.this.itemId);
               qs.setStatus(QuestStatus.REWARD);
               _2033DestroyingtheCurse.this.updateQuestStatus(player, qs);
             }
           }3000L);
     }
     return true;
   }
}
