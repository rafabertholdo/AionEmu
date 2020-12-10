package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.services.GroupService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

public class CM_INVITE_TO_GROUP extends AionClientPacket {
  private String name;
  private int inviteType;

  public CM_INVITE_TO_GROUP(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.inviteType = readC();
    this.name = readS();
  }

  protected void runImpl() {
    String playerName = Util.convertName(this.name);

    Player inviter = ((AionConnection) getConnection()).getActivePlayer();
    Player invited = World.getInstance().findPlayer(playerName);

    if (invited != null) {

      if (invited.getPlayerSettings().isInDeniedStatus(DeniedStatus.GROUP)) {

        sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_INVITE_PARTY(invited.getName()));
        return;
      }
      if (this.inviteType == 0) {
        GroupService.getInstance().invitePlayerToGroup(inviter, invited);
      } else if (this.inviteType == 10) {
        AllianceService.getInstance().invitePlayerToAlliance(inviter, invited);
      } else {
        PacketSendUtility.sendMessage(inviter, "You used an unknown invite type: " + this.inviteType);
      }
    } else {
      inviter.getClientConnection().sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.PLAYER_IS_OFFLINE(this.name));
    }
  }
}
