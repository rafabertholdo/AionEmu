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

public class CM_SHOW_DIALOG extends AionClientPacket {
  private int targetObjectId;

  public CM_SHOW_DIALOG(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetObjectId = readD();
  }

  protected void runImpl() {
    AionObject targetObject = World.getInstance().findAionObject(this.targetObjectId);
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    if (targetObject == null || player == null) {
      return;
    }
    if (targetObject instanceof Npc) {

      ((Npc) targetObject).setTarget((VisibleObject) player);

      PacketSendUtility.broadcastPacket((VisibleObject) targetObject,
          (AionServerPacket) new SM_LOOKATOBJECT((VisibleObject) targetObject));

      ((Npc) targetObject).getController().onDialogRequest(player);
    }
  }
}
