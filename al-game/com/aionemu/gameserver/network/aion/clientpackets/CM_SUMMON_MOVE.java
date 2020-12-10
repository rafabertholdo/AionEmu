package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.controllers.movement.MovementType;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOVE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class CM_SUMMON_MOVE extends AionClientPacket {
  private static final Logger log = Logger.getLogger(CM_SUMMON_MOVE.class);

  private MovementType type;

  private byte heading;

  private byte movementType;

  private float x;

  private float y;
  private float z;
  private float x2;
  private float y2;
  private float z2;

  public CM_SUMMON_MOVE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    if (!player.isSpawned()) {
      return;
    }
    readD();

    this.x = readF();
    this.y = readF();
    this.z = readF();

    this.heading = (byte) readC();
    this.movementType = (byte) readC();
    this.type = MovementType.getMovementTypeById(this.movementType);

    switch (this.type) {

      case MOVEMENT_START_MOUSE:
      case MOVEMENT_START_KEYBOARD:
        this.x2 = readF();
        this.y2 = readF();
        this.z2 = readF();
        break;
    }
  }

  protected void runImpl() {
    StringBuilder sb;
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    if (player == null) {

      log.error("CM_SUMMON_MOVE packet received but cannot get master player.");
      return;
    }
    Summon summon = player.getSummon();
    if (summon == null) {
      return;
    }
    if (this.type == null) {
      return;
    }
    switch (this.type) {

      case MOVEMENT_START_MOUSE:
      case MOVEMENT_START_KEYBOARD:
        World.getInstance().updatePosition((VisibleObject) summon, this.x, this.y, this.z, this.heading);
        PacketSendUtility.broadcastPacket((VisibleObject) summon, (AionServerPacket) new SM_MOVE(summon.getObjectId(),
            this.x, this.y, this.z, this.x2, this.y2, this.z2, this.heading, this.type));
        break;
      case VALIDATE_MOUSE:
      case VALIDATE_KEYBOARD:
        PacketSendUtility.broadcastPacket((VisibleObject) summon,
            (AionServerPacket) new SM_MOVE(summon.getObjectId(), this.x, this.y, this.z, this.x2, this.y2, this.z2,
                this.heading, (this.type == MovementType.VALIDATE_MOUSE) ? MovementType.MOVEMENT_START_MOUSE
                    : MovementType.MOVEMENT_START_KEYBOARD));
        break;

      case MOVEMENT_STOP:
        PacketSendUtility.broadcastPacket((VisibleObject) summon,
            (AionServerPacket) new SM_MOVE(summon.getObjectId(), this.x, this.y, this.z, this.heading, this.type));
        World.getInstance().updatePosition((VisibleObject) summon, this.x, this.y, this.z, this.heading);
        break;
      case UNKNOWN:
        sb = new StringBuilder();
        sb.append("Unknown movement type: ").append(this.movementType);
        sb.append("Coordinates: X=").append(this.x);
        sb.append(" Y=").append(this.y);
        sb.append(" Z=").append(this.z);
        sb.append(" player=").append(player.getName());
        log.warn(sb.toString());
        break;
    }
  }
}
