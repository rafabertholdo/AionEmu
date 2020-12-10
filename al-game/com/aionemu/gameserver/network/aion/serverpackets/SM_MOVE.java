package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.controllers.movement.MovementType;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_MOVE extends AionServerPacket {
  private final int movingCreatureId;
  private final float x;
  private final float y;
  private final float z;
  private final float x2;
  private final float y2;
  private final float z2;
  private final byte heading;
  private final MovementType moveType;
  private final byte glideFlag;
  private boolean hasDirection;
  private boolean hasGlideFlag;

  public SM_MOVE(int movingCreatureId, float x, float y, float z, float x2, float y2, float z2, byte heading,
      byte glideFlag, MovementType moveType) {
    this.movingCreatureId = movingCreatureId;
    this.x = x;
    this.y = y;
    this.z = z;
    this.x2 = x2;
    this.y2 = y2;
    this.z2 = z2;
    this.heading = heading;
    this.glideFlag = glideFlag;
    this.moveType = moveType;

    this.hasDirection = true;
    this.hasGlideFlag = true;
  }

  public SM_MOVE(int movingCreatureId, float x, float y, float z, float x2, float y2, float z2, byte heading,
      MovementType moveType) {
    this(movingCreatureId, x, y, z, x2, y2, z2, heading, (byte) 0, moveType);
    this.hasDirection = true;
    this.hasGlideFlag = false;
  }

  public SM_MOVE(int movingCreatureId, float x, float y, float z, byte heading, MovementType moveType) {
    this(movingCreatureId, x, y, z, 0.0F, 0.0F, 0.0F, heading, (byte) 0, moveType);
    this.hasDirection = false;
    this.hasGlideFlag = false;
  }

  public SM_MOVE(int movingCreatureId, float x, float y, float z, byte heading, byte glideFlag, MovementType moveType) {
    this(movingCreatureId, x, y, z, 0.0F, 0.0F, 0.0F, heading, glideFlag, moveType);
    this.hasDirection = false;
    this.hasGlideFlag = true;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.movingCreatureId);
    writeF(buf, this.x);
    writeF(buf, this.y);
    writeF(buf, this.z);
    writeC(buf, this.heading);
    writeC(buf, this.moveType.getMovementTypeId());

    if (this.hasDirection) {

      writeF(buf, this.x2);
      writeF(buf, this.y2);
      writeF(buf, this.z2);
    }

    if (this.hasGlideFlag) {
      writeC(buf, this.glideFlag);
    }
  }
}
