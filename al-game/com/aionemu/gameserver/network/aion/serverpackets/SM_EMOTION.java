package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_EMOTION extends AionServerPacket {
  private int senderObjectId;
  private EmotionType emotionType;
  private int emotion;
  private int targetObjectId;
  private float speed = 6.0F;

  private int state;

  private int baseAttackSpeed;

  private int currentAttackSpeed;

  private float x;

  private float y;

  private float z;

  private byte heading;

  public SM_EMOTION(Creature creature, EmotionType emotionType) {
    this(creature, emotionType, 0, 0);
  }

  public SM_EMOTION(Creature creature, EmotionType emotionType, int emotion, int targetObjectId) {
    this.senderObjectId = creature.getObjectId();
    this.emotionType = emotionType;
    this.emotion = emotion;
    this.targetObjectId = targetObjectId;
    this.state = creature.getState();
    this.baseAttackSpeed = creature.getGameStats().getBaseStat(StatEnum.ATTACK_SPEED);
    this.currentAttackSpeed = creature.getGameStats().getCurrentStat(StatEnum.ATTACK_SPEED);

    if (creature.isInState(CreatureState.FLYING)) {
      this.speed = creature.getGameStats().getCurrentStat(StatEnum.FLY_SPEED) / 1000.0F;
    } else {
      this.speed = creature.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F;
    }
  }

  public SM_EMOTION(int doorId) {
    this.senderObjectId = doorId;
    this.emotionType = EmotionType.SWITCH_DOOR;
  }

  public SM_EMOTION(Player player, EmotionType emotionType, int emotion, float x, float y, float z, byte heading,
      int targetObjectId) {
    this.senderObjectId = player.getObjectId();
    this.emotionType = emotionType;
    this.emotion = emotion;
    this.x = x;
    this.y = y;
    this.z = z;
    this.heading = heading;
    this.targetObjectId = targetObjectId;

    if (player.isInState(CreatureState.FLYING)) {
      this.speed = player.getGameStats().getCurrentStat(StatEnum.FLY_SPEED) / 1000.0F;
    } else {
      this.speed = player.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F;
    }
    this.state = player.getState();
    this.baseAttackSpeed = player.getGameStats().getBaseStat(StatEnum.ATTACK_SPEED);
    this.currentAttackSpeed = player.getGameStats().getCurrentStat(StatEnum.ATTACK_SPEED);
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.senderObjectId);
    writeC(buf, this.emotionType.getTypeId());
    switch (this.emotionType) {

      case SELECT_TARGET:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case JUMP:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case SIT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case STAND:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case CHAIR_SIT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeF(buf, this.x);
        writeF(buf, this.y);
        writeF(buf, this.z);
        writeC(buf, this.heading);
        return;

      case CHAIR_UP:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeF(buf, this.x);
        writeF(buf, this.y);
        writeF(buf, this.z);
        writeC(buf, this.heading);
        return;

      case START_FLYTELEPORT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeD(buf, this.emotion);
        return;

      case LAND_FLYTELEPORT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case FLY:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case LAND:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case DIE:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeD(buf, this.targetObjectId);
        return;

      case RESURRECT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case EMOTE:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeD(buf, this.targetObjectId);
        writeH(buf, this.emotion);
        writeC(buf, 1);
        return;

      case ATTACKMODE:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case NEUTRALMODE:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case WALK:
        writeH(buf, this.state);
        writeF(buf, this.speed - this.speed * 75.0F / 100.0F);
        return;

      case RUN:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case SWITCH_DOOR:
        writeH(buf, 9);
        writeD(buf, 0);
        return;

      case START_EMOTE:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeH(buf, this.baseAttackSpeed);
        writeH(buf, this.currentAttackSpeed);
        return;

      case OPEN_PRIVATESHOP:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case CLOSE_PRIVATESHOP:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case START_EMOTE2:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeH(buf, this.baseAttackSpeed);
        writeH(buf, this.currentAttackSpeed);
        return;

      case POWERSHARD_ON:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case POWERSHARD_OFF:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case ATTACKMODE2:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case NEUTRALMODE2:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        return;

      case START_LOOT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeD(buf, this.targetObjectId);
        return;

      case END_LOOT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeD(buf, this.targetObjectId);
        return;

      case START_QUESTLOOT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeD(buf, this.targetObjectId);
        return;

      case END_QUESTLOOT:
        writeH(buf, this.state);
        writeF(buf, this.speed);
        writeD(buf, this.targetObjectId);
        return;
    }
    writeH(buf, this.state);
    writeF(buf, this.speed);
    if (this.targetObjectId != 0) {
      writeD(buf, this.targetObjectId);
    }
  }
}
