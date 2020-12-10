package com.aionemu.gameserver.taskmanager;

import com.aionemu.commons.utils.concurrent.ExecuteWrapper;




















public abstract class FIFORunnableQueue<T extends Runnable>
  extends FIFOSimpleExecutableQueue<T>
{
  protected final void removeAndExecuteFirst() {
    ExecuteWrapper.execute((Runnable)removeFirst(), 5000L);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\FIFORunnableQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
