package com.aionemu.gameserver.skillengine.task;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.concurrent.Future;






















public abstract class AbstractInteractionTask
{
  private Future<?> task;
  private int interval = 2500;


  
  protected Player requestor;

  
  protected VisibleObject responder;


  
  public AbstractInteractionTask(Player requestor, VisibleObject responder) {
    this.requestor = requestor;
    if (responder == null) {
      this.responder = (VisibleObject)requestor;
    } else {
      this.responder = responder;
    } 
  }




  
  protected abstract boolean onInteraction();




  
  protected abstract void onInteractionFinish();



  
  protected abstract void onInteractionStart();



  
  protected abstract void onInteractionAbort();



  
  public void start() {
    onInteractionStart();
    
    this.task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
        {
          
          public void run()
          {
            if (!AbstractInteractionTask.this.validateParticipants()) {
              AbstractInteractionTask.this.stop();
            }
            boolean stopTask = AbstractInteractionTask.this.onInteraction();
            if (stopTask) {
              AbstractInteractionTask.this.stop();
            }
          }
        },  1000L, this.interval);
  }




  
  public void stop() {
    onInteractionFinish();
    
    if (this.task != null && !this.task.isCancelled()) {
      
      this.task.cancel(true);
      this.task = null;
    } 
  }




  
  public void abort() {
    onInteractionAbort();
    stop();
  }




  
  public boolean isInProgress() {
    return (this.task != null && !this.task.isCancelled());
  }




  
  public boolean validateParticipants() {
    return (this.requestor != null);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\task\AbstractInteractionTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
