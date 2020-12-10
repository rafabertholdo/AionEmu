package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LOOKATOBJECT extends AionServerPacket {
  private VisibleObject visibleObject;
  private int targetObjectId;
  private int heading;

  public SM_LOOKATOBJECT(VisibleObject visibleObject) {
    this.visibleObject = visibleObject;
    if (visibleObject.getTarget() != null) {

      this.targetObjectId = visibleObject.getTarget().getObjectId();
      this.heading = Math.abs(128 - visibleObject.getTarget().getHeading());
    } else {

      this.targetObjectId = 0;
      this.heading = visibleObject.getHeading();
    }
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.visibleObject.getObjectId());
    writeD(buf, this.targetObjectId);
    writeC(buf, this.heading);
  }
}
