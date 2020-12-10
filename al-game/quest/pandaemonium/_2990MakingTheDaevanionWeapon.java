package quest.pandaemonium;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _2990MakingTheDaevanionWeapon extends QuestHandler {
    private static final int questId = 2990;
    private static final int[] npc_ids = new int[] { 204146 };

    int ALL = 0;
    int A = 0;
    int B = 0;
    int C = 0;

    public _2990MakingTheDaevanionWeapon() {
        super(Integer.valueOf(2990));
    }

    public void register() {
        this.qe.setNpcQuestData(204146).addOnQuestStart(2990);
        this.qe.setNpcQuestData(256617).addOnKillEvent(2990);
        this.qe.setNpcQuestData(253720).addOnKillEvent(2990);
        this.qe.setNpcQuestData(254513).addOnKillEvent(2990);
        for (int npc_id : npc_ids) {
            this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2990);
        }
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestState qs = player.getQuestStateList().getQuestState(2990);

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 204146) {

                if (env.getDialogId().intValue() == 25) {

                    int plate = player.getEquipment().itemSetPartsEquipped(9);
                    int chain = player.getEquipment().itemSetPartsEquipped(8);
                    int leather = player.getEquipment().itemSetPartsEquipped(7);
                    int cloth = player.getEquipment().itemSetPartsEquipped(6);

                    if (plate != 5 && chain != 5 && leather != 5 && cloth != 5) {
                        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4848);
                    }
                    return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
                }

                return defaultQuestStartDialog(env);
            }
        }

        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int var1 = qs.getQuestVarById(1);

        if (qs.getStatus() == QuestStatus.START) {

            if (targetId == 204146) {
                switch (env.getDialogId().intValue()) {

                    case 25:
                        if (var == 0)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
                        if (var == 1)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                        if (var == 2 && var1 == 60)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                        if (var == 3 && player.getInventory().getItemCountByItemId(186000040) > 0L)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
                    case 33:
                        if (var == 0) {

                            if (QuestService.collectItemCheck(env, true)) {

                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(player, qs);
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
                            }

                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
                        }
                        break;
                    case 1352:
                        if (var == 0)
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                    case 2035:
                        if (var == 3 && player.getInventory().getItemCountByItemId(186000040) > 0L) {

                            if (player.getCommonData().getDp() < 4000) {
                                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
                            }

                            ItemService.removeItemFromInventoryByItemId(player, 186000040);
                            player.getCommonData().setDp(0);
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(player, qs);
                            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                        }
                        break;

                    case 10001:
                        if (var == 1) {

                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(player, qs);
                            PacketSendUtility.sendPacket(player,
                                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                            return true;
                        }
                        break;
                    case 10002:
                        if (var == 2) {

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
        if (qs.getStatus() == QuestStatus.REWARD) {

            if (targetId == 204146) {
                return defaultQuestEndDialog(env);
            }
            return false;
        }
        return false;
    }

    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(2990);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if ((targetId == 256617 || targetId == 253720 || targetId == 254513) && qs.getQuestVarById(0) == 2) {
            switch (targetId) {

                case 256617:
                    if (this.A >= 0 && this.A < 30) {

                        this.A++;
                        this.ALL = this.C;
                        this.ALL <<= 7;
                        this.ALL += this.B;
                        this.ALL <<= 7;
                        this.ALL += this.A;
                        this.ALL <<= 7;
                        this.ALL += 2;
                        qs.setQuestVar(this.ALL);
                        updateQuestStatus(player, qs);
                    }
                    break;
                case 253720:
                    if (this.B >= 0 && this.B < 30) {

                        this.B++;
                        this.ALL = this.C;
                        this.ALL <<= 7;
                        this.ALL += this.B;
                        this.ALL <<= 7;
                        this.ALL += this.A;
                        this.ALL <<= 7;
                        this.ALL += 2;
                        qs.setQuestVar(this.ALL);
                        updateQuestStatus(player, qs);
                    }
                    break;
                case 254513:
                    if (this.C >= 0 && this.C < 30) {

                        this.ALL = ++this.C;
                        this.ALL <<= 7;
                        this.ALL += this.B;
                        this.ALL <<= 7;
                        this.ALL += this.A;
                        this.ALL <<= 7;
                        this.ALL += 2;
                        qs.setQuestVar(this.ALL);
                        updateQuestStatus(player, qs);
                    }
                    break;
            }

        }
        if (qs.getQuestVarById(0) == 2 && this.A == 30 && this.B == 30 && this.C == 30) {

            qs.setQuestVarById(1, 60);
            updateQuestStatus(player, qs);
            return true;
        }
        return false;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\pandaemonium\_2990MakingTheDaevanionWeapon.class Java compiler
 * version: 6 (50.0) JD-Core Version: 1.1.3
 */
