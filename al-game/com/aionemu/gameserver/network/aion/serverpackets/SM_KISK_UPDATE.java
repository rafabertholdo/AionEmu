package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_KISK_UPDATE extends AionServerPacket {
  private int objId;
  private int useMask;
  private int currentMembers;
  private int maxMembers;
  private int remainingRessurects;
  private int maxRessurects;
  private int remainingLifetime;

  public SM_KISK_UPDATE(Kisk kisk) {
    this.objId = kisk.getObjectId();
    this.useMask = kisk.getUseMask();
    this.currentMembers = kisk.getCurrentMemberCount();
    this.maxMembers = kisk.getMaxMembers();
    this.remainingRessurects = kisk.getRemainingResurrects();
    this.maxRessurects = kisk.getMaxRessurects();
    this.remainingLifetime = kisk.getRemainingLifetime();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.objId);
    writeD(buf, this.useMask);
    writeD(buf, this.currentMembers);
    writeD(buf, this.maxMembers);
    writeD(buf, this.remainingRessurects);
    writeD(buf, this.maxRessurects);
    writeD(buf, this.remainingLifetime);
  }
}
