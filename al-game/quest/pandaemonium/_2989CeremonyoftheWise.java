package quest.pandaemonium;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _2989CeremonyoftheWise extends QuestHandler {
    private static final int questId = 2989;

    public _2989CeremonyoftheWise() {
        super(Integer.valueOf(2989));
    }

    public void register() {
        this.qe.setNpcQuestData(204056).addOnQuestStart(2989);
        this.qe.setNpcQuestData(204057).addOnQuestStart(2989);
        this.qe.setNpcQuestData(204058).addOnQuestStart(2989);
        this.qe.setNpcQuestData(204059).addOnQuestStart(2989);
        this.qe.setNpcQuestData(204146).addOnQuestStart(2989);
        this.qe.setNpcQuestData(204146).addOnTalkEvent(2989);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(2989);

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 204146) {

                if (env.getDialogId().intValue() == 25) {
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                }
                return defaultQuestStartDialog(env);
            }
        }

        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {

            PlayerClass playerClass = player.getCommonData().getPlayerClass();
            switch (targetId) {

                case 204056:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (playerClass == PlayerClass.GLADIATOR || playerClass == PlayerClass.TEMPLAR) {
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                            }
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
                        case 10000:
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                    }
                case 204057:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (playerClass == PlayerClass.ASSASSIN || playerClass == PlayerClass.RANGER) {
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                            }
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
                        case 10000:
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                    }
                case 204058:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (playerClass == PlayerClass.SORCERER || playerClass == PlayerClass.SPIRIT_MASTER) {
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                            }
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
                        case 10000:
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                    }
                case 204059:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (playerClass == PlayerClass.CLERIC || playerClass == PlayerClass.CHANTER) {
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                            }
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);
                        case 10000:
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                    }
                case 204146:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (var == 1)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
                            if (var == 2)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
                            if (var == 3) {

                                if (player.getCommonData().getDp() < 4000) {
                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3484);
                                }
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398);
                            }
                            if (var == 4) {

                                if (player.getCommonData().getDp() < 4000) {
                                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3825);
                                }
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739);
                            }
                        case 1009:
                            if (var == 3) {

                                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 137));
                                player.getCommonData().setDp(0);
                                qs.setStatus(QuestStatus.REWARD);
                                updateQuestStatus(player, qs);
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                            }
                            if (var == 4) {

                                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 137));
                                player.getCommonData().setDp(0);
                                qs.setStatus(QuestStatus.REWARD);
                                updateQuestStatus(player, qs);
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                            }

                            return defaultQuestEndDialog(env);
                        case 10001:
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
                        case 10003:
                            qs.setQuestVarById(0, 3);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                        case 10004:
                            qs.setQuestVarById(0, 4);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                    }
                    break;
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 204146)
                return defaultQuestEndDialog(env);
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\pandaemonium\_2989CeremonyoftheWise.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */
