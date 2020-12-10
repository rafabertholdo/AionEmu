package quest.altgard;

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

public class _2016FearThis extends QuestHandler {
  private static final int questId = 2016;

  public _2016FearThis() {
    super(Integer.valueOf(2016));
  }

  public void register() {
    this.qe.addQuestLvlUp(2016);
    this.qe.setNpcQuestData(203631).addOnTalkEvent(2016);
    this.qe.setNpcQuestData(210455).addOnKillEvent(2016);
    this.qe.setNpcQuestData(210458).addOnKillEvent(2016);
    this.qe.setNpcQuestData(214032).addOnKillEvent(2016);
    this.qe.setNpcQuestData(203621).addOnTalkEvent(2016);
    this.qe.setQuestItemIds(182203019).add(2016);
    this.deletebleItems = new int[] { 182203019 };
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2016);
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

        case 203631:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
              if (var == 6)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
            case 1012:
              PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 63));
              break;
            case 10000:
            case 10001:
              if (var == 0 || var == 6) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
        case 203621:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 7)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
              if (var == 8)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
              break;
            case 10002:
            case 10003:
              if (var == 7 || var == 9) {

                if (var == 9 && !ItemService.addItems(player, Collections.singletonList(new QuestItems(182203019, 1))))
                  return true;
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
            case 33:
              if (var == 8) {

                if (QuestService.collectItemCheck(env, true)) {

                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
                }

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
              }
              break;
          }
          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203631) {

        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        }
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, final Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182203019)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.Q2016))
      return false; 
    final QuestState qs = player.getQuestStateList().getQuestState(2016);
    if (qs == null)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            ItemService.removeItemFromInventory(player, item);
            qs.setStatus(QuestStatus.REWARD);
            _2016FearThis.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2016);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }

    int targetId = 0;
    int var = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    switch (targetId) {

      case 210455:
      case 210458:
      case 214032:
        var = qs.getQuestVarById(0);
        if (var < 6) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
        }
        break;
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2016);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}
