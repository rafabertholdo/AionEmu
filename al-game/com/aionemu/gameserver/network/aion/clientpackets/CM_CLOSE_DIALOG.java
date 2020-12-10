package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;





















public class CM_CLOSE_DIALOG
  extends AionClientPacket
{
  private int targetObjectId;
  
  public CM_CLOSE_DIALOG(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.targetObjectId = readD();
  }





  
  protected void runImpl() {
    AionObject targetObject = World.getInstance().findAionObject(this.targetObjectId);
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    if (targetObject == null || player == null) {
      return;
    }
    if (targetObject instanceof Npc) {

      
      if (((Npc)targetObject).getTarget() == player) {
        ((Npc)targetObject).setTarget(null);
      }
      PacketSendUtility.broadcastPacket((VisibleObject)targetObject, (AionServerPacket)new SM_LOOKATOBJECT((VisibleObject)targetObject));
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CLOSE_DIALOG.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
