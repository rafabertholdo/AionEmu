package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.PetitionService;
import java.nio.ByteBuffer;

public class SM_PETITION extends AionServerPacket {
  private Petition petition;

  public SM_PETITION() {
    this.petition = null;
  }

  public SM_PETITION(Petition petition) {
    this.petition = petition;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (this.petition == null) {

      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeH(buf, 0);
      writeC(buf, 0);
    } else {

      writeC(buf, 1);
      writeD(buf, 100);
      writeH(buf, PetitionService.getInstance().getWaitingPlayers(con.getActivePlayer().getObjectId()));
      writeS(buf, Integer.toString(this.petition.getPetitionId()));
      writeH(buf, 0);
      writeC(buf, 50);
      writeC(buf, 49);
      writeH(buf, PetitionService.getInstance().calculateWaitTime(this.petition.getPlayerObjId()));
      writeD(buf, 0);
    }
  }
}
