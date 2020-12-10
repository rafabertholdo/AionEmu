package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CM_CLIENT_COMMAND_ROLL extends AionClientPacket {
  private int maxRoll;
  private int roll;

  public CM_CLIENT_COMMAND_ROLL(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.maxRoll = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    if (player == null) {
      return;
    }
    this.roll = Rnd.get(1, this.maxRoll);
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1400126,
        new Object[] { Integer.valueOf(this.roll), Integer.valueOf(this.maxRoll) }));
    PacketSendUtility.broadcastPacket((VisibleObject) player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1400127,
        new Object[] { player.getName(), Integer.valueOf(this.roll), Integer.valueOf(this.maxRoll) }));
  }
}
