package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _1514TheEttinsNecklace extends QuestHandler {
    private static final int questId = 1514;

    public _1514TheEttinsNecklace() {
        super(Integer.valueOf(1514));
    }

    public void register() {
        this.qe.setNpcQuestData(203831).addOnTalkEvent(1514);
        this.qe.setNpcQuestData(204582).addOnTalkEvent(1514);
        this.qe.setNpcQuestData(204505).addOnTalkEvent(1514);
        this.qe.setQuestItemIds(182201710).add(1514);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1514);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        if (targetId == 0) {

            if (env.getDialogId().intValue() == 1002) {
                QuestService.startQuest(env, QuestStatus.START);
                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_DIALOG_WINDOW(0, 0));
                return true;
            }

        } else if (targetId == 204582) {

            if (qs != null) {
                if (env.getDialogId().intValue() == 25)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                if (env.getDialogId().intValue() == 10000) {

                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(player, qs);
                    PacketSendUtility.sendPacket(player,
                            (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                    return true;
                }

                return defaultQuestStartDialog(env);
            }

        } else if (targetId == 204505) {

            if (qs != null) {
                if (env.getDialogId().intValue() == 25)
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                if (env.getDialogId().intValue() == 10000) {

                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(player, qs);
                    PacketSendUtility.sendPacket(player,
                            (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                    return true;
                }

                return defaultQuestStartDialog(env);
            }

        } else if (targetId == 203831) {

            if (qs != null) {

                if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                }
                if (env.getDialogId().intValue() == 1009) {

                    ItemService.removeItemFromInventoryByItemId(player, 182201710);
                    qs.setQuestVar(1);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return defaultQuestEndDialog(env);
                }

                return defaultQuestEndDialog(env);
            }
        }
        return false;
    }

  public boolean onItemUseEvent(QuestEnv env, Item item) {
    final Player player = env.getPlayer();
    final int id = item.getItemTemplate().getTemplateId();
    final int itemObjId = item.getObjectId();
    
    if (id != 182201710)
      return false; 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
            
            _1514TheEttinsNecklace.this.sendQuestDialog(player, 0, 4);
          }
        }3000L);
    return true;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\heiron\_1514TheEttinsNecklace.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */