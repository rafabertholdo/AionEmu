package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.controllers.attack.AttackResult;
import com.aionemu.gameserver.controllers.attack.AttackStatus;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Collections;

public final class AggressionDesire extends AbstractDesire {
    protected Npc npc;

    public AggressionDesire(Npc npc, int desirePower) {
        super(desirePower);
        this.npc = npc;
    }

  public boolean handleDesire(AI<?> ai) {
    if (this.npc == null) return false;
    
    for (VisibleObject visibleObject : this.npc.getKnownList().getKnownObjects().values()) {
      
      if (visibleObject == null) {
        continue;
      }
      if (visibleObject instanceof Creature) {
        
        final Creature creature = (Creature)visibleObject;
        
        if (!creature.getLifeStats().isAlreadyDead() && MathUtil.isIn3dRange((VisibleObject)this.npc, (VisibleObject)creature, this.npc.getAggroRange())) {


          
          if (!this.npc.canSee(visibleObject)) {
            continue;
          }
          if (!this.npc.isAggressiveTo(creature)) {
            continue;
          }
          
          this.npc.getAi().setAiState(AIState.NONE);
          PacketSendUtility.broadcastPacket((VisibleObject)this.npc, (AionServerPacket)new SM_ATTACK((Creature)this.npc, creature, 0, 633, 0, Collections.singletonList(new AttackResult(0, AttackStatus.NORMALHIT))));

          
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  AggressionDesire.this.npc.getAggroList().addHate(creature, 1);
                }
              }1000L);
          break;
        } 
      } 
    } 
    return true;
  }

    public int getExecutionInterval() {
        return 2;
    }

    public void onClear() {
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\com\aionemu\gameserver\ai\desires\impl\AggressionDesire.class Java compiler
 * version: 6 (50.0) JD-Core Version: 1.1.3
 */
