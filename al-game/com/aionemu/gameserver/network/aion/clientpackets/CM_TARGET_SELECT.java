package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_SELECTED;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_UPDATE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

public class CM_TARGET_SELECT extends AionClientPacket {
  private int targetObjectId;
  private int type;

  public CM_TARGET_SELECT(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetObjectId = readD();
    this.type = readC();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    if (player == null) {
      return;
    }
    AionObject obj = World.getInstance().findAionObject(this.targetObjectId);
    if (obj != null && obj instanceof VisibleObject) {

      if (this.type == 1) {
        if (((VisibleObject) obj).getTarget() == null)
          return;
        player.setTarget(((VisibleObject) obj).getTarget());
      } else {
        player.setTarget((VisibleObject) obj);
      }

    } else {

      player.setTarget(null);
    }
    sendPacket((AionServerPacket) new SM_TARGET_SELECTED(player));
    PacketSendUtility.broadcastPacket((VisibleObject) player, (AionServerPacket) new SM_TARGET_UPDATE(player));
  }
}
