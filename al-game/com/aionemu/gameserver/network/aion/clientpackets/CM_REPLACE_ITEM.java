package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ItemService;

public class CM_REPLACE_ITEM extends AionClientPacket {
  private int sourceStorageType;
  private int sourceItemObjId;
  private int replaceStorageType;
  private int replaceItemObjId;

  public CM_REPLACE_ITEM(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.sourceStorageType = readC();
    this.sourceItemObjId = readD();
    this.replaceStorageType = readC();
    this.replaceItemObjId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    ItemService.switchStoragesItems(player, this.sourceStorageType, this.sourceItemObjId, this.replaceStorageType,
        this.replaceItemObjId);
  }
}
