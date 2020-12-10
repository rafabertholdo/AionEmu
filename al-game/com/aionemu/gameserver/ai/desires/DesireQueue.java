package com.aionemu.gameserver.ai.desires;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.PriorityQueue;


































public class DesireQueue
{
  protected PriorityQueue<Desire> queue;
  
  public synchronized Desire peek() {
    return (this.queue != null) ? this.queue.peek() : null;
  }






  
  public synchronized Desire poll() {
    if (this.queue != null)
    {
      return this.queue.poll();
    }
    
    return null;
  }



















  
  public synchronized void addDesire(Desire desire) {
    if (this.queue == null)
    {
      this.queue = new PriorityQueue<Desire>();
    }

    
    Iterator<Desire> iterator = this.queue.iterator();
    while (iterator.hasNext()) {
      
      Desire iterated = iterator.next();

      
      if (desire.equals(iterated)) {

        
        iterator.remove();



        
        if (desire != iterated)
        {
          desire.increaseDesirePower(iterated.getDesirePower());
        }

        
        break;
      } 
    } 
    
    this.queue.add(desire);
  }









  
  public synchronized boolean removeDesire(Desire desire) {
    return (this.queue != null && this.queue.remove(desire));
  }


























  
  public synchronized void iterateDesires(DesireIteratorHandler handler, DesireIteratorFilter... filters) throws ConcurrentModificationException {
    if (this.queue == null) {
      return;
    }

    
    Iterator<Desire> iterator = this.queue.iterator();
    label20: while (iterator.hasNext()) {
      
      Desire desire = iterator.next();
      
      if (filters != null && filters.length > 0)
      {
        for (DesireIteratorFilter filter : filters) {
          
          if (!filter.isOk(desire)) {
            continue label20;
          }
        } 
      }

      
      handler.next(desire, iterator);
    } 
  }









  
  public synchronized boolean contains(Desire desire) {
    return this.queue.contains(desire);
  }






  
  public synchronized boolean isEmpty() {
    return (this.queue == null || this.queue.isEmpty());
  }




  
  public synchronized void clear() {
    if (this.queue != null) {
      
      Desire desire = null;
      while ((desire = this.queue.poll()) != null) {
        desire.onClear();
      }
    } 
  }





  
  public synchronized int size() {
    return (this.queue == null) ? 0 : this.queue.size();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\DesireQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
