package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.DesireIteratorHandler;
import java.util.Iterator;



























public class GeneralDesireIteratorHandler
  implements DesireIteratorHandler
{
  private AI ai;
  
  public GeneralDesireIteratorHandler(AI ai) {
    this.ai = ai;
  }


  
  public void next(Desire desire, Iterator<Desire> iterator) {
    boolean isOk = desire.handleDesire(this.ai);
    if (!isOk) {
      
      desire.onClear();
      iterator.remove();
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\GeneralDesireIteratorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
