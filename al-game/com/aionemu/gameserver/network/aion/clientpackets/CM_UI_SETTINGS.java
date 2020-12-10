package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;

public class CM_UI_SETTINGS extends AionClientPacket {
  int settingsType;
  byte[] data;
  int size;

  public CM_UI_SETTINGS(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.settingsType = readC();
    readH();
    this.size = readH();
    this.data = readB(getRemainingBytes());
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    if (player == null) {
      return;
    }
    if (this.settingsType == 0) {

      player.getPlayerSettings().setUiSettings(this.data);
    } else if (this.settingsType == 1) {

      player.getPlayerSettings().setShortcuts(this.data);
    }
  }
}
