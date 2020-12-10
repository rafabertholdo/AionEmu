package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.Collections;

public class _1466RespectForDeltras extends QuestHandler {
  private static final int questId = 1466;

  public _1466RespectForDeltras() {
    super(Integer.valueOf(1466));
  }

  public void register() {
    this.qe.setQuestItemIds(182201385).add(1466);
    this.qe.setNpcQuestData(212649).addOnQuestStart(1466);
    this.qe.setNpcQuestData(212649).addOnTalkEvent(1466);
    this.qe.setNpcQuestData(203903).addOnTalkEvent(1466);
  }

  public boolean onItemUseEvent(QuestEnv env, final Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182201385)
      return false; 
    if (!ZoneService.getInstance().isInsideZone(player, ZoneName.Q1466))
      return false; 
    final QuestState qs = player.getQuestStateList().getQuestState(1466);
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
            _1466RespectForDeltras.this.updateQuestStatus(player, qs);
          }
        }3000L);
    return false;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1466);
    if (targetId == 212649) {

      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        if (env.getDialogId().intValue() == 1002) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182201385, 1)))) {
            return defaultQuestStartDialog(env);
          }
          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 203903) {

      if (qs != null) {

        if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        if (env.getDialogId().intValue() == 1009) {

          qs.setQuestVar(2);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        }

        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}
