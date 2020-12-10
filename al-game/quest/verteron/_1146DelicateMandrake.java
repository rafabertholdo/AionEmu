package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _1146DelicateMandrake extends QuestHandler {
    private static final int questId = 1146;

    public _1146DelicateMandrake() {
        super(Integer.valueOf(1146));
    }

    public void register() {
        this.qe.setNpcQuestData(203123).addOnQuestStart(1146);
        this.qe.setNpcQuestData(203123).addOnTalkEvent(1146);
        this.qe.setNpcQuestData(203139).addOnTalkEvent(1146);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(1146);
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {

            if (targetId == 203123) {
                switch (env.getDialogId().intValue()) {

                    case 25:
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                    case 1007:
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4);
                    case 1002:
                        if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200519, 1))))
                            ;

                        QuestService.questTimerStart(env, 900);
                        if (QuestService.startQuest(env, QuestStatus.START)) {
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1003);
                        }
                        return false;
                    case 1003:
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1004);
                }

            }
        } else if (targetId == 203139) {

            if ((qs.getQuestVarById(0) == 0 || qs.getQuestVarById(0) == 0) && env.getDialogId().intValue() == -1) {

                if (ItemService.removeItemFromInventoryByItemId(player, 182200011)) {

                    qs.setQuestVar(2);
                    QuestService.questTimerEnd(env);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(player, qs);
                    return defaultQuestEndDialog(env);
                }

                PacketSendUtility.sendPacket(player,
                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return defaultQuestEndDialog(env);
            }
        }
        return false;
    }

    public boolean onQuestTimerEndEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(1146);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        PacketSendUtility.sendMessage(player,
                "ToDo: Set what happens when timer ends..... And remove temp from QuestService");

        return true;
    }
}
