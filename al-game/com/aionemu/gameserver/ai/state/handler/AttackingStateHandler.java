package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.impl.AttackDesire;
import com.aionemu.gameserver.ai.desires.impl.MoveToTargetDesire;
import com.aionemu.gameserver.ai.desires.impl.SkillUseDesire;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class AttackingStateHandler extends StateHandler {
  public AIState getState() {
    return AIState.ATTACKING;
  }

  public void handleState(AIState state, AI<?> ai) {
    ai.clearDesires();

    Creature target = ((Npc) ai.getOwner()).getAggroList().getMostHated();
    if (target == null) {
      return;
    }
    Npc owner = (Npc) ai.getOwner();
    owner.setTarget((VisibleObject) target);
    PacketSendUtility.broadcastPacket((VisibleObject) owner,
        (AionServerPacket) new SM_LOOKATOBJECT((VisibleObject) owner));

    owner.setState(CreatureState.WEAPON_EQUIPPED);
    PacketSendUtility.broadcastPacket((VisibleObject) owner,
        (AionServerPacket) new SM_EMOTION((Creature) owner, EmotionType.START_EMOTE2, 0, target.getObjectId()));

    PacketSendUtility.broadcastPacket((VisibleObject) owner,
        (AionServerPacket) new SM_EMOTION((Creature) owner, EmotionType.ATTACKMODE, 0, target.getObjectId()));

    owner.getMoveController().setSpeed(owner.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F);
    owner.getMoveController().setDistance(owner.getGameStats().getCurrentStat(StatEnum.ATTACK_RANGE) / 1000.0F);

    if (owner.getNpcSkillList() != null)
      ai.addDesire((Desire) new SkillUseDesire((Creature) owner, AIState.USESKILL.getPriority()));
    ai.addDesire((Desire) new AttackDesire(owner, target, AIState.ATTACKING.getPriority()));
    if (owner.getGameStats().getCurrentStat(StatEnum.SPEED) != 0) {
      ai.addDesire((Desire) new MoveToTargetDesire(owner, target, AIState.ATTACKING.getPriority()));
    }
    ai.schedule();
  }
}
