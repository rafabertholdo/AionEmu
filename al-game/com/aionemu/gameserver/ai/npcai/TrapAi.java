package com.aionemu.gameserver.ai.npcai;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.events.handler.EventHandler;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.ai.state.handler.NoneNpcStateHandler;
import com.aionemu.gameserver.ai.state.handler.StateHandler;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Trap;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.ThreadPoolManager;























public class TrapAi
  extends NpcAi
{
  public TrapAi() {
    addEventHandler(new SeeObjectEventHandler());



    
    addStateHandler(new ActiveTrapStateHandler());
    addStateHandler((StateHandler)new NoneNpcStateHandler());
  }

  
  public class SeeObjectEventHandler
    implements EventHandler
  {
    public Event getEvent() {
      return Event.SEE_CREATURE;
    }


    
    public void handleEvent(Event event, AI<?> ai) {
      ai.setAiState(AIState.ACTIVE);
      if (!ai.isScheduled()) {
        ai.analyzeState();
      }
    }
  }

  
  class ActiveTrapStateHandler
    extends StateHandler
  {
    public AIState getState() {
      return AIState.ACTIVE;
    }


    
    public void handleState(AIState state, AI<?> ai) {
      ai.clearDesires();
      Trap owner = (Trap)ai.getOwner();
      Creature trapCreator = owner.getCreator();
      
      int enemyCount = 0;
      for (VisibleObject visibleObject : owner.getKnownList().getKnownObjects().values()) {
        
        if (trapCreator.isEnemy(visibleObject)) {
          enemyCount++;
        }
      } 
      if (enemyCount > 0)
      {
        ai.addDesire((Desire)new TrapAi.TrapExplodeDesire(owner, trapCreator, AIState.ACTIVE.getPriority()));
      }
      
      if (ai.desireQueueSize() == 0) {
        ai.handleEvent(Event.NOTHING_TODO);
      } else {
        ai.schedule();
      } 
    }
  }




  
  class TrapExplodeDesire
    extends AbstractDesire
  {
    private Trap owner;


    
    private Creature creator;



    
    private TrapExplodeDesire(Trap owner, Creature creator, int desirePower) {
      super(desirePower);
      this.owner = owner;
      this.creator = creator;
    }


    
    public boolean handleDesire(AI<?> ai) {
      for (VisibleObject visibleObject : this.owner.getKnownList().getKnownObjects().values()) {
        
        if (visibleObject == null) {
          continue;
        }
        if (visibleObject instanceof Creature) {
          
          Creature creature = (Creature)visibleObject;
          
          if (!creature.getLifeStats().isAlreadyDead() && MathUtil.isIn3dRange((VisibleObject)this.owner, (VisibleObject)creature, this.owner.getAggroRange())) {


            
            if (!this.creator.isEnemy((VisibleObject)creature)) {
              continue;
            }
            this.owner.getAi().setAiState(AIState.NONE);
            
            int skillId = this.owner.getSkillId();
            Skill skill = SkillEngine.getInstance().getSkill((Creature)this.owner, skillId, 1, (VisibleObject)creature);
            skill.useSkill();
            ThreadPoolManager.getInstance().schedule(new Runnable()
                {
                  public void run()
                  {
                    TrapAi.TrapExplodeDesire.this.owner.getController().onDespawn(true);
                  }
                },  (skill.getSkillTemplate().getDuration() + 1000));
            break;
          } 
        } 
      } 
      return true;
    }


    
    public int getExecutionInterval() {
      return 2;
    }
    
    public void onClear() {}
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\npcai\TrapAi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
