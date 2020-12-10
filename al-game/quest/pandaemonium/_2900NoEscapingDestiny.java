package quest.pandaemonium;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.InstanceService;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.ArrayList;
import java.util.List;

public class _2900NoEscapingDestiny extends QuestHandler {
    private static final int questId = 2900;

    public _2900NoEscapingDestiny() {
        super(Integer.valueOf(2900));
    }

    public void register() {
        this.qe.addQuestLvlUp(2900);
        this.qe.setNpcQuestData(204182).addOnTalkEvent(2900);
        this.qe.setNpcQuestData(203550).addOnTalkEvent(2900);
        this.qe.setNpcQuestData(790003).addOnTalkEvent(2900);
        this.qe.setNpcQuestData(790002).addOnTalkEvent(2900);
        this.qe.setNpcQuestData(203546).addOnTalkEvent(2900);
        this.qe.setNpcQuestData(204264).addOnTalkEvent(2900);
        this.qe.setQuestMovieEndIds(156).add(2900);
        this.qe.setNpcQuestData(204263).addOnKillEvent(2900);
        this.qe.setNpcQuestData(204061).addOnTalkEvent(2900);
        this.qe.addOnEnterWorld(2900);
        this.qe.addOnDie(2900);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int instanceId = player.getInstanceId();
        QuestState qs = player.getQuestStateList().getQuestState(2900);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVars().getQuestVars();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs.getStatus() == QuestStatus.START) {

            switch (targetId) {

                case 204182:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (var == 0)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                        case 10000:
                            if (var == 0) {

                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                            }
                            break;
                    }
                    break;
                case 203550:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (var == 1)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                            if (var == 10)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080);
                        case 10001:
                            if (var == 1) {

                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                            }
                            break;
                        case 10009:
                            if (var == 10) {

                                qs.setStatus(QuestStatus.REWARD);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                            }
                            break;
                    }
                    break;
                case 790003:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (var == 2)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                        case 10002:
                            if (var == 2) {

                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                            }
                            break;
                    }
                    break;
                case 790002:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (var == 3)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                        case 10003:
                            if (var == 3) {

                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                10));
                                return true;
                            }
                            break;
                    }
                    break;
                case 203546:
                    switch (env.getDialogId().intValue()) {

                        case 25:
                            if (var == 4)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                            if (var == 9)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739);
                            break;
                        case 10004:
                            if (var == 4) {

                                qs.setQuestVar(95);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                0));
                                WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(320070000);
                                InstanceService.registerPlayerWithInstance(newInstance, player);
                                TeleportService.teleportTo(player, 320070000, newInstance.getInstanceId(), 257.5F,
                                        245.0F, 129.0F, 0);
                                return true;
                            }
                            break;
                        case 10008:
                            if (var == 9) {

                                removeStigma(player);
                                qs.setQuestVar(10);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                0));
                                return true;
                            }
                            break;
                    }
                    break;
                case 204264:
                    switch (env.getDialogId().intValue()) {

                        case -1:
                            if (var == 99) {

                                int itemId = getStoneId(player);
                                if (player.getEquipment().getEquippedItemsByItemId(itemId).size() != 0) {

                                    qs.setQuestVar(97);
                                    updateQuestStatus(player, qs);
                                }
                                return false;
                            }
                            break;
                        case 25:
                            if (var == 95)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
                            if (var == 99)
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
                            if (var == 97) {
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398);
                            }
                            break;
                        case 10005:
                            if (var == 95) {

                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                0));
                                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 156));
                                return true;
                            }
                            break;
                        case 10007:
                            if (var == 97) {

                                qs.setQuestVar(98);
                                updateQuestStatus(player, qs);
                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                0));
                                QuestService.addNewSpawn(320070000, instanceId, 204263, 257.5F, 245.0F, 129.0F,
                                        (byte) 0, true);
                                return true;
                            }
                            break;
                        case 3058:
                            if (var == 99) {

                                int itemId = getStoneId(player);
                                if (player.getInventory().getItemCountByItemId(itemId) > 0L)
                                    return false;
                                List<QuestItems> items = new ArrayList<QuestItems>();
                                items.add(new QuestItems(itemId, 1));
                                items.add(new QuestItems(141000001, 60));
                                if (!ItemService.addItems(player, items)) {
                                    return true;
                                }
                                return false;
                            }
                            break;
                        case 10006:
                            if (var == 99) {

                                PacketSendUtility.sendPacket(player,
                                        (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(),
                                                1));
                                return true;
                            }
                            break;
                    }
                    break;
            }
        } else if (qs.getStatus() == QuestStatus.REWARD && targetId == 204061) {
            return defaultQuestEndDialog(env);
        }
        return false;
    }

    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(2900);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (targetId == 204263 && qs.getQuestVars().getQuestVars() == 98) {

            qs.setQuestVar(9);
            updateQuestStatus(player, qs);
            TeleportService.teleportTo(player, 220010000, 1, 1111.6F, 1716.6F, 270.6F, 0);
            return true;
        }
        return false;
    }

    public boolean onLvlUpEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(2900);
        if (qs != null)
            return false;
        boolean lvlCheck = QuestService.checkLevelRequirement(2900, player.getCommonData().getLevel());
        if (!lvlCheck)
            return false;
        env.setQuestId(Integer.valueOf(2900));
        QuestService.startQuest(env, QuestStatus.START);
        return true;
    }

    public boolean onMovieEndEvent(QuestEnv env, int movieId) {
        if (movieId != 156)
            return false;
        Player player = env.getPlayer();
        env.setQuestId(Integer.valueOf(2900));
        QuestState qs = player.getQuestStateList().getQuestState(2900);
        if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 95)
            return false;
        qs.setQuestVar(99);
        updateQuestStatus(player, qs);
        return true;
    }

    public boolean onDieEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(2900);
        if (qs == null || qs.getStatus() != QuestStatus.START)
            return false;
        int var = qs.getQuestVars().getQuestVars();
        if (var > 90) {

            removeStigma(player);
            qs.setQuestVar(4);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1,
                            new Object[] { DataManager.QUEST_DATA.getQuestById(2900).getName() }));
        }
        return false;
    }

    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(2900);
        if (qs != null && qs.getStatus() == QuestStatus.START) {

            int var = qs.getQuestVars().getQuestVars();
            if (var > 90) {
                if (player.getWorldId() != 320070000) {

                    removeStigma(player);
                    qs.setQuestVar(4);
                    updateQuestStatus(player, qs);
                    PacketSendUtility.sendPacket(player,
                            (AionServerPacket) new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1,
                                    new Object[] { DataManager.QUEST_DATA.getQuestById(2900).getName() }));
                }
            }
        }
        return false;
    }

    private int getStoneId(Player player) {
        switch (player.getCommonData().getPlayerClass()) {

            case GLADIATOR:
                return 140000008;
            case TEMPLAR:
                return 140000027;
            case RANGER:
                return 140000047;
            case ASSASSIN:
                return 140000076;
            case SORCERER:
                return 140000131;
            case SPIRIT_MASTER:
                return 140000147;
            case CLERIC:
                return 140000098;
            case CHANTER:
                return 140000112;
        }
        return 0;
    }

    private void removeStigma(Player player) {
        int itemId = getStoneId(player);
        List<Item> items = player.getEquipment().getEquippedItemsByItemId(itemId);
        Equipment equipment = player.getEquipment();
        for (Item item : items) {
            equipment.unEquipItem(item.getObjectId(), 0);
        }
        ItemService.removeItemFromInventoryByItemId(player, itemId);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\pandaemonium\_2900NoEscapingDestiny.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */
