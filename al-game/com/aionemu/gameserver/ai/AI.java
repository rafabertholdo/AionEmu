package com.aionemu.gameserver.ai;

import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.DesireIteratorFilter;
import com.aionemu.gameserver.ai.desires.DesireIteratorHandler;
import com.aionemu.gameserver.ai.desires.DesireQueue;
import com.aionemu.gameserver.ai.desires.impl.CounterBasedDesireFilter;
import com.aionemu.gameserver.ai.desires.impl.GeneralDesireIteratorHandler;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.events.handler.EventHandler;
import com.aionemu.gameserver.ai.npcai.DummyAi;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.ai.state.handler.StateHandler;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.Map;
import java.util.concurrent.Future;
import javolution.util.FastMap;



















public abstract class AI<T extends Creature>
  implements Runnable
{
  private static final DummyAi dummyAi = new DummyAi();
  
  protected Map<Event, EventHandler> eventHandlers = (Map<Event, EventHandler>)new FastMap();
  protected Map<AIState, StateHandler> stateHandlers = (Map<AIState, StateHandler>)new FastMap();
  
  protected DesireQueue desireQueue = new DesireQueue();
  
  protected Creature owner;
  
  protected AIState aiState = AIState.NONE;




  
  protected boolean isStateChanged = false;



  
  private Future<?> aiTask;




  
  public void handleEvent(Event event) {}




  
  public void handleTalk(Player player) {}




  
  public Creature getOwner() {
    return this.owner;
  }





  
  public void setOwner(Creature owner) {
    this.owner = owner;
  }




  
  public AIState getAiState() {
    return this.aiState;
  }





  
  protected void addEventHandler(EventHandler eventHandler) {
    this.eventHandlers.put(eventHandler.getEvent(), eventHandler);
  }





  
  protected void addStateHandler(StateHandler stateHandler) {
    this.stateHandlers.put(stateHandler.getState(), stateHandler);
  }




  
  public void setAiState(AIState aiState) {
    if (this.aiState != aiState) {
      
      this.aiState = aiState;
      this.isStateChanged = true;
    } 
  }

  
  public void analyzeState() {
    this.isStateChanged = false;
    StateHandler stateHandler = this.stateHandlers.get(this.aiState);
    if (stateHandler != null) {
      stateHandler.handleState(this.aiState, this);
    }
  }

  
  public void run() {
    this.desireQueue.iterateDesires((DesireIteratorHandler)new GeneralDesireIteratorHandler(this), new DesireIteratorFilter[] { (DesireIteratorFilter)new CounterBasedDesireFilter() });
    
    if (this.desireQueue.isEmpty() || this.isStateChanged)
    {
      analyzeState();
    }
  }

  
  public void schedule() {
    if (!isScheduled())
    {
      this.aiTask = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(this, 1000L, 1000L);
    }
  }

  
  public void stop() {
    if (this.aiTask != null && !this.aiTask.isCancelled()) {
      
      this.aiTask.cancel(true);
      this.aiTask = null;
    } 
  }

  
  public boolean isScheduled() {
    return (this.aiTask != null && !this.aiTask.isCancelled());
  }

  
  public void clearDesires() {
    this.desireQueue.clear();
  }

  
  public void addDesire(Desire desire) {
    this.desireQueue.addDesire(desire);
  }

  
  public int desireQueueSize() {
    return this.desireQueue.isEmpty() ? 0 : this.desireQueue.size();
  }




  
  public static DummyAi dummyAi() {
    return dummyAi;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\AI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
