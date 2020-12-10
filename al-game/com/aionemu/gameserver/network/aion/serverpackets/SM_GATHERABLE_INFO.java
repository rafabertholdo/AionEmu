package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_GATHERABLE_INFO extends AionServerPacket {
  private VisibleObject visibleObject;

  public SM_GATHERABLE_INFO(VisibleObject visibleObject) {
    this.visibleObject = visibleObject;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeF(buf, this.visibleObject.getX());
    writeF(buf, this.visibleObject.getY());
    writeF(buf, this.visibleObject.getZ());
    writeD(buf, this.visibleObject.getObjectId());
    writeD(buf, this.visibleObject.getSpawn().getStaticid());
    writeD(buf, this.visibleObject.getObjectTemplate().getTemplateId());
    writeH(buf, 1);
    writeC(buf, 0);
    writeD(buf, this.visibleObject.getObjectTemplate().getNameId());
    writeH(buf, 0);
    writeH(buf, 0);
    writeH(buf, 0);
    writeC(buf, 100);
  }
}
