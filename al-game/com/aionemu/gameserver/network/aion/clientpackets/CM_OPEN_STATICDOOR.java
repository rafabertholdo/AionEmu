package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CM_OPEN_STATICDOOR extends AionClientPacket {
  private int doorId;

  public CM_OPEN_STATICDOOR(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.doorId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject) player, (AionServerPacket) new SM_EMOTION(this.doorId));
  }
}
