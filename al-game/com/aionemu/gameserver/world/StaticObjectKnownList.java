package com.aionemu.gameserver.world;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;

























public final class StaticObjectKnownList
  extends KnownList
{
  public StaticObjectKnownList(VisibleObject owner) {
    super(owner);
  }







  
  protected final boolean addKnownObject(VisibleObject object) {
    if (object instanceof com.aionemu.gameserver.model.gameobjects.player.Player) {
      return super.addKnownObject(object);
    }
    return false;
  }





  
  protected final void findVisibleObjects() {
    if (getOwner() == null || !getOwner().isSpawned()) {
      return;
    }
    for (MapRegion region : getOwner().getActiveRegion().getNeighbours()) {
      
      for (VisibleObject object : region.getVisibleObjects().values()) {
        
        if (!(object instanceof com.aionemu.gameserver.model.gameobjects.player.Player)) {
          continue;
        }
        addKnownObject(object);
        object.getKnownList().addKnownObject(getOwner());
      } 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\StaticObjectKnownList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
