package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class PostboxController extends NpcController {
  public void onDialogRequest(Player player) {
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_DIALOG_WINDOW(getOwner().getObjectId(), 18));
  }
}
