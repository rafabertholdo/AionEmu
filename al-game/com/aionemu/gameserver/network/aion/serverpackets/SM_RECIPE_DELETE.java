package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;






















public class SM_RECIPE_DELETE
  extends AionServerPacket
{
  private int recipeId;
  
  public SM_RECIPE_DELETE(int recipeId) {
    this.recipeId = recipeId;
  }


  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.recipeId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_RECIPE_DELETE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
