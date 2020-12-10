package com.aionemu.gameserver.world.container;

import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
import java.util.Map;
import javolution.util.FastMap;























public class KiskContainer
{
  private final Map<Integer, Kisk> kiskByPlayerObjectId = (Map<Integer, Kisk>)(new FastMap()).shared();

  
  public void add(Kisk kisk, Player player) {
    if (this.kiskByPlayerObjectId.put(Integer.valueOf(player.getObjectId()), kisk) != null) {
      throw new DuplicateAionObjectException();
    }
  }
  
  public Kisk get(Player player) {
    return this.kiskByPlayerObjectId.get(Integer.valueOf(player.getObjectId()));
  }

  
  public void remove(Player player) {
    this.kiskByPlayerObjectId.remove(Integer.valueOf(player.getObjectId()));
  }




  
  public int getCount() {
    return this.kiskByPlayerObjectId.size();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\container\KiskContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
