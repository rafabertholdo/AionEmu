package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.impl.MoveToHomeDesire;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class MovingToHomeStateHandler extends StateHandler {
  public AIState getState() {
    return AIState.MOVINGTOHOME;
  }

  public void handleState(AIState state, AI<?> ai) {
    ai.clearDesires();
    Npc npc = (Npc) ai.getOwner();
    npc.setTarget(null);
    PacketSendUtility.broadcastPacket((VisibleObject) npc, (AionServerPacket) new SM_LOOKATOBJECT((VisibleObject) npc));
    npc.getAggroList().clear();
    PacketSendUtility.broadcastPacket((VisibleObject) npc,
        (AionServerPacket) new SM_EMOTION((Creature) npc, EmotionType.START_EMOTE2, 0, 0));
    PacketSendUtility.broadcastPacket((VisibleObject) npc,
        (AionServerPacket) new SM_EMOTION((Creature) npc, EmotionType.NEUTRALMODE, 0, 0));
    ai.addDesire((Desire) new MoveToHomeDesire(npc, AIState.MOVINGTOHOME.getPriority()));

    ai.schedule();
  }
}
