package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CM_EQUIP_ITEM extends AionClientPacket {
  public int slotRead;
  public int itemUniqueId;
  public int action;

  public CM_EQUIP_ITEM(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.action = readC();
    this.slotRead = readD();
    this.itemUniqueId = readD();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    Equipment equipment = activePlayer.getEquipment();
    Item resultItem = null;

    if (!RestrictionsManager.canChangeEquip(activePlayer)) {
      return;
    }
    switch (this.action) {

      case 0:
        resultItem = equipment.equipItem(this.itemUniqueId, this.slotRead);
        break;
      case 1:
        resultItem = equipment.unEquipItem(this.itemUniqueId, this.slotRead);
        break;
      case 2:
        equipment.switchHands();
        break;
    }

    if (resultItem != null || this.action == 2) {
      PacketSendUtility.broadcastPacket(activePlayer,
          (AionServerPacket) new SM_UPDATE_PLAYER_APPEARANCE(activePlayer.getObjectId(),
              equipment.getEquippedItemsWithoutStigma()),
          true);
    }
  }
}
