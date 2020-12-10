package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.configs.main.FallDamageConfig;
import com.aionemu.gameserver.controllers.MoveController;
import com.aionemu.gameserver.controllers.movement.MovementType;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOVE;
import com.aionemu.gameserver.taskmanager.tasks.GroupAllianceUpdater;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class CM_MOVE extends AionClientPacket {
  private static final Logger log = Logger.getLogger(CM_MOVE.class);

  private MovementType type;

  private byte heading;

  private byte movementType;

  private float x;

  private float y;

  private float z;
  private float x2;
  private float y2;
  private float z2;
  private byte glideFlag;

  public CM_MOVE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    if (!player.isSpawned()) {
      return;
    }
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
      case MOVEMENT_GLIDE_DOWN:
      case MOVEMENT_GLIDE_START_MOUSE:
        this.x2 = readF();
        this.y2 = readF();
        this.z2 = readF();

      case MOVEMENT_GLIDE_UP:
      case VALIDATE_GLIDE_MOUSE:
        this.glideFlag = (byte) readC();
        break;
    }
  }

  protected void runImpl() {
    float glideSpeed;
    double angle;
    MoveController mc;
    StringBuilder sb;
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    World world = World.getInstance();

    if (this.type == null) {
      return;
    }
    float playerZ = player.getZ();

    switch (this.type) {

      case MOVEMENT_START_MOUSE:
      case MOVEMENT_START_KEYBOARD:
      case MOVEMENT_MOVIN_ELEVATOR:
      case MOVEMENT_ON_ELEVATOR:
      case MOVEMENT_STAYIN_ELEVATOR:
        world.updatePosition((VisibleObject) player, this.x, this.y, this.z, this.heading);
        player.getMoveController().setNewDirection(this.x2, this.y2, this.z2);
        player.getController().onStartMove();
        player.getFlyController().onStopGliding();
        PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_MOVE(player.getObjectId(), this.x, this.y,
            this.z, this.x2, this.y2, this.z2, this.heading, this.type), false);
        break;

      case MOVEMENT_GLIDE_START_MOUSE:
        player.getMoveController().setNewDirection(this.x2, this.y2, this.z2);

      case MOVEMENT_GLIDE_DOWN:
        world.updatePosition((VisibleObject) player, this.x, this.y, this.z, this.heading);
        player.getController().onMove();
        PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_MOVE(player.getObjectId(), this.x, this.y,
            this.z, this.x2, this.y2, this.z2, this.heading, this.glideFlag, this.type), false);

        player.getFlyController().switchToGliding();
        break;
      case MOVEMENT_GLIDE_UP:
        world.updatePosition((VisibleObject) player, this.x, this.y, this.z, this.heading);
        player.getController().onMove();
        PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_MOVE(player.getObjectId(), this.x, this.y,
            this.z, this.heading, this.glideFlag, this.type), false);

        player.getFlyController().switchToGliding();
        break;
      case VALIDATE_GLIDE_MOUSE:
        world.updatePosition((VisibleObject) player, this.x, this.y, this.z, this.heading);
        player.getController().onMove();
        player.getFlyController().switchToGliding();

        glideSpeed = player.getGameStats().getCurrentStat(StatEnum.SPEED);
        angle = Math.toRadians((this.heading * 3));
        this.x2 = (float) (glideSpeed * Math.cos(angle));
        this.y2 = (float) (glideSpeed * Math.sin(angle));

        PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_MOVE(player.getObjectId(), this.x, this.y,
            this.z, this.x2, this.y2, this.z2, this.heading, this.glideFlag, MovementType.MOVEMENT_GLIDE_DOWN), false);
        break;

      case VALIDATE_MOUSE:
      case VALIDATE_KEYBOARD:
        player.getController().onMove();
        player.getFlyController().onStopGliding();
        world.updatePosition((VisibleObject) player, this.x, this.y, this.z, this.heading);

        mc = player.getMoveController();

        PacketSendUtility.broadcastPacket(player,
            (AionServerPacket) new SM_MOVE(player.getObjectId(), this.x, this.y, this.z, mc.getTargetX(),
                mc.getTargetY(), mc.getTargetZ(), this.heading,
                (this.type == MovementType.VALIDATE_MOUSE) ? MovementType.MOVEMENT_START_MOUSE
                    : MovementType.MOVEMENT_START_KEYBOARD),
            false);
        break;

      case MOVEMENT_STOP:
        PacketSendUtility.broadcastPacket(player,
            (AionServerPacket) new SM_MOVE(player.getObjectId(), this.x, this.y, this.z, this.heading, this.type),
            false);

        world.updatePosition((VisibleObject) player, this.x, this.y, this.z, this.heading);
        player.getController().onStopMove();
        player.getFlyController().onStopGliding();
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

    if (player.isInGroup() || player.isInAlliance()) {
      GroupAllianceUpdater.getInstance().add(player);
    }

    float distance = playerZ - this.z;
    if (FallDamageConfig.ACTIVE_FALL_DAMAGE && player.isInState(CreatureState.ACTIVE)
        && !player.isInState(CreatureState.FLYING) && !player.isInState(CreatureState.GLIDING)
        && (this.type == MovementType.MOVEMENT_STOP || distance >= FallDamageConfig.MAXIMUM_DISTANCE_MIDAIR)) {

      if (StatFunctions.calculateFallDamage(player, distance)) {
        return;
      }
    }

    if (this.type != MovementType.MOVEMENT_STOP && player.isProtectionActive()) {
      player.getController().stopProtectionActiveTask();
    }
  }
}
