package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import org.apache.log4j.Logger;

public class CM_SUMMON_EMOTION extends AionClientPacket {
  private static final Logger log = Logger.getLogger(CM_SUMMON_EMOTION.class);

  private int objId;

  private int emotionTypeId;

  public CM_SUMMON_EMOTION(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.objId = readD();
    this.emotionTypeId = readC();
  }

  protected void runImpl() {
    EmotionType emotionType = EmotionType.getEmotionTypeById(this.emotionTypeId);

    if (emotionType == EmotionType.UNK) {
      log.error("Unknown emotion type? 0x" + Integer.toHexString(this.emotionTypeId).toUpperCase());
    }
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    if (activePlayer == null)
      return;
    Summon summon = activePlayer.getSummon();
    if (summon == null)
      return;
    switch (emotionType) {

      case FLY:
      case LAND:
        PacketSendUtility.broadcastPacket((VisibleObject) summon,
            (AionServerPacket) new SM_EMOTION((Creature) summon, emotionType));
        break;
      case ATTACKMODE:
        summon.setState(CreatureState.WEAPON_EQUIPPED);
        PacketSendUtility.broadcastPacket((VisibleObject) summon,
            (AionServerPacket) new SM_EMOTION((Creature) summon, emotionType));
        break;
      case NEUTRALMODE:
        summon.unsetState(CreatureState.WEAPON_EQUIPPED);
        PacketSendUtility.broadcastPacket((VisibleObject) summon,
            (AionServerPacket) new SM_EMOTION((Creature) summon, emotionType));
        break;
    }
  }
}
