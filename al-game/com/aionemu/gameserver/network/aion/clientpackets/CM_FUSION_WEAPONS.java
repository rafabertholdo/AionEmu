package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ArmsfusionService;

















public class CM_FUSION_WEAPONS
  extends AionClientPacket
{
  private int firstItemId;
  private int secondItemId;
  private int price;
  
  public CM_FUSION_WEAPONS(int opcode) {
    super(opcode);
  }









  
  protected void readImpl() {
    readD();
    this.firstItemId = readD();
    this.secondItemId = readD();



    
    this.price = 50000;
  }





  
  protected void runImpl() {
    ArmsfusionService.fusionWeapons(((AionConnection)getConnection()).getActivePlayer(), this.firstItemId, this.secondItemId, this.price);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_FUSION_WEAPONS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
