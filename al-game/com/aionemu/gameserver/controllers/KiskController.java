package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.KiskService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import java.util.List;

public class KiskController extends NpcController {
    public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
        Kisk kisk = (Kisk) getOwner();

        if (kisk.getLifeStats().isFullyRestoredHp()) {

            List<Player> members = kisk.getCurrentMemberList();
            for (Player member : members) {
                PacketSendUtility.sendPacket(member, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_BINDSTONE_IS_ATTACKED);
            }
        }

        super.onAttack(creature, skillId, type, damage);
    }

    public void onDespawn(boolean forced) {
        Kisk kisk = (Kisk) getOwner();
        kisk.broadcastPacket(SM_SYSTEM_MESSAGE.STR_BINDSTONE_IS_REMOVED);
        removeKisk(kisk);
    }

    public void onDie(Creature lastAttacker) {
        Kisk kisk = (Kisk) getOwner();
        PacketSendUtility.broadcastPacket((VisibleObject) kisk,
                (AionServerPacket) new SM_EMOTION((Creature) kisk, EmotionType.DIE, 0, 0));
        kisk.broadcastPacket(SM_SYSTEM_MESSAGE.STR_BINDSTONE_IS_DESTROYED);
        removeKisk(kisk);
    }

    private void removeKisk(final Kisk kisk) {
        KiskService.removeKisk(kisk);

        addTask(TaskId.DECAY, ThreadPoolManager.getInstance().schedule(new Runnable() {

            public void run() {
                if (kisk != null && kisk.isSpawned()) {
                    World.getInstance().despawn((VisibleObject) kisk);
                }
            }
        }, 3000L));
    }

    public void onDialogRequest(Player player) {
        Kisk kisk = (Kisk) getOwner();

        if (player.getKisk() == kisk) {

            PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_BINDSTONE_ALREADY_REGISTERED);

            return;
        }
        if (kisk.canBind(player)) {

            RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) kisk) {

                public void acceptRequest(Creature requester, Player responder) {
                    Kisk kisk = (Kisk) requester;

                    if (!kisk.canBind(responder)) {

                        PacketSendUtility.sendPacket(responder,
                                (AionServerPacket) SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_BINDSTONE_HAVE_NO_AUTHORITY);
                        return;
                    }
                    KiskService.onBind(kisk, responder);
                }

                public void denyRequest(Creature requester, Player responder) {
                }
            };
            boolean requested = player.getResponseRequester().putRequest(160018, responseHandler);
            if (requested) {
                PacketSendUtility.sendPacket(player,
                        (AionServerPacket) new SM_QUESTION_WINDOW(160018, player.getObjectId(), new Object[0]));
            }
        } else if (kisk.getCurrentMemberCount() >= kisk.getMaxMembers()) {

            PacketSendUtility.sendPacket(player,
                    (AionServerPacket) SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_BINDSTONE_FULL);
        } else {

            PacketSendUtility.sendPacket(player,
                    (AionServerPacket) SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_BINDSTONE_HAVE_NO_AUTHORITY);
        }
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\com\aionemu\gameserver\controllers\KiskController.class Java compiler
 * version: 6 (50.0) JD-Core Version: 1.1.3
 */
