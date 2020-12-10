package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;































public class CM_ATTACK
  extends AionClientPacket
{
  private int targetObjectId;
  private int attackno;
  private int time;
  private int type;
  private long exp;
  private long maxexp;
  private int at;
  
  public CM_ATTACK(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.targetObjectId = readD();
    this.attackno = readC();
    this.time = readH();
    this.type = readC();
  }





  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    if (player != null && !player.getLifeStats().isAlreadyDead()) {
      
      VisibleObject visibleObject = (VisibleObject)player.getKnownList().getKnownObjects().get(Integer.valueOf(this.targetObjectId));
      if (visibleObject instanceof Creature)
        player.getController().attackTarget((Creature)visibleObject); 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_ATTACK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
