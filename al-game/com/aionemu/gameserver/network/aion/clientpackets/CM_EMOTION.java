package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import org.apache.log4j.Logger;























public class CM_EMOTION
  extends AionClientPacket
{
  private static final Logger log = Logger.getLogger(CM_EMOTION.class);


  
  EmotionType emotionType;


  
  int emotion;


  
  float x;


  
  float y;

  
  float z;

  
  byte heading;


  
  public CM_EMOTION(int opcode) {
    super(opcode);
  }






  
  protected void readImpl() {
    int et = readC();
    this.emotionType = EmotionType.getEmotionTypeById(et);
    
    switch (this.emotionType) {
      case SELECT_TARGET:
      case JUMP:
      case SIT:
      case STAND:
      case LAND_FLYTELEPORT:
      case FLY:
      case LAND:
      case DIE:
      case ATTACKMODE:
      case NEUTRALMODE:
      case END_DUEL:
      case WALK:
      case RUN:
      case SWITCH_DOOR:
      case POWERSHARD_ON:
      case POWERSHARD_OFF:
      case ATTACKMODE2:
      case NEUTRALMODE2:
        return;
      
      case EMOTE:
        this.emotion = readH();
      
      case CHAIR_SIT:
      case CHAIR_UP:
        this.x = readF();
        this.y = readF();
        this.z = readF();
        this.heading = (byte)readC();
    } 
    
    log.error("Unknown emotion type? 0x" + Integer.toHexString(et).toUpperCase());
  }






  
  protected void runImpl() {
    ZoneInstance currentZone;
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    switch (this.emotionType) {
      case SELECT_TARGET:
        return;
      
      case SIT:
        player.setState(CreatureState.RESTING);
        break;
      case STAND:
        player.unsetState(CreatureState.RESTING);
        break;
      case CHAIR_SIT:
        player.unsetState(CreatureState.ACTIVE);
        player.setState(CreatureState.CHAIR);
        break;
      case CHAIR_UP:
        player.unsetState(CreatureState.CHAIR);
        player.setState(CreatureState.ACTIVE);
        break;
      case LAND_FLYTELEPORT:
        player.getController().onFlyTeleportEnd();
        break;
      
      case FLY:
        currentZone = player.getZoneInstance();
        if (currentZone != null) {
          
          boolean flightAllowed = currentZone.getTemplate().isFlightAllowed();
          if (!flightAllowed) {
            
            PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FLYING_FORBIDDEN_HERE);
            return;
          } 
        } 
        player.getFlyController().startFly();
        break;
      case LAND:
        player.getFlyController().endFly();
        break;
      case ATTACKMODE:
      case ATTACKMODE2:
        player.setState(CreatureState.WEAPON_EQUIPPED);
        break;
      case NEUTRALMODE:
      case NEUTRALMODE2:
        player.unsetState(CreatureState.WEAPON_EQUIPPED);
        break;
      
      case WALK:
        if (player.getFlyState() > 0)
          return; 
        player.setState(CreatureState.WALKING);
        break;
      case RUN:
        player.unsetState(CreatureState.WALKING);
        break;


      
      case POWERSHARD_ON:
        if (!player.getEquipment().isPowerShardEquipped()) {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.NO_POWER_SHARD_EQUIPPED());
          return;
        } 
        PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.ACTIVATE_THE_POWER_SHARD());
        player.setState(CreatureState.POWERSHARD);
        break;
      case POWERSHARD_OFF:
        PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.DEACTIVATE_THE_POWER_SHARD());
        player.unsetState(CreatureState.POWERSHARD);
        break;
    } 
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION(player, this.emotionType, this.emotion, this.x, this.y, this.z, this.heading, (player.getTarget() == null) ? 0 : player.getTarget().getObjectId()), true);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_EMOTION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
