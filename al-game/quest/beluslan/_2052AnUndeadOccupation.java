package quest.beluslan;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
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

public class _2052AnUndeadOccupation extends QuestHandler {
  private static final int questId = 2052;
  private static final int[] npc_ids = new int[] { 204715, 204801, 204805 };

  public _2052AnUndeadOccupation() {
    super(Integer.valueOf(2052));
  }

  public void register() {
    this.qe.addQuestLvlUp(2052);
    this.qe.setQuestItemIds(182204304).add(2052);
    this.qe.setNpcQuestData(213044).addOnKillEvent(2052);
    for (int npc_id : npc_ids)
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2052);
    this.deletebleItems = new int[] { 182204304 };
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2052);
    boolean lvlCheck = QuestService.checkLevelRequirement(2052, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(2500);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2052);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 204715) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        return defaultQuestEndDialog(env);
      }
      return false;
    }
    if (qs.getStatus() != QuestStatus.START) {
      return false;
    }
    if (targetId == 204715) {

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
    } else if (targetId == 204801) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          if (var == 12)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        case 10001:
          if (var == 1) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
        case 10002:
          if (var == 12) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          break;
      }
    } else if (targetId == 204805) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 13)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
          if (var == 14)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        case 33:
          if (QuestService.collectItemCheck(env, true)) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182204304, 1)));
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
        case 10003:
          if (var == 13) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          break;
      }
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2052);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId == 213044 && qs.getQuestVarById(0) > 1 && qs.getQuestVarById(0) < 12) {

      qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
      updateQuestStatus(player, qs);
      return true;
    }
    return false;
  }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    final QuestState qs = player.getQuestStateList().getQuestState(2052);
    
    if (qs == null || qs.getQuestVarById(0) != 15 || id != 182204304)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.BERITRAS_WEAPON_220040000)) {
      return false;
    }
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            ItemService.removeItemFromInventoryByItemId(player, 182204304);
            qs.setStatus(QuestStatus.REWARD);
            _2052AnUndeadOccupation.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return true;
  }
}
