package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.world.WorldPosition;
import java.nio.ByteBuffer;
import java.util.List;






















public class SM_ALLIANCE_MEMBER_INFO
  extends AionServerPacket
{
  private PlayerAllianceMember member;
  private PlayerAllianceEvent event;
  
  public SM_ALLIANCE_MEMBER_INFO(PlayerAllianceMember member, PlayerAllianceEvent event) {
    this.member = member;
    this.event = event;
  }


  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    PlayerCommonData pcd = this.member.getCommonData();
    WorldPosition wp = pcd.getPosition();





    
    if (!this.member.isOnline()) {
      this.event = PlayerAllianceEvent.DISCONNECTED;
    }
    writeD(buf, this.member.getAllianceId());
    writeD(buf, this.member.getObjectId());
    if (this.member.isOnline()) {
      
      PlayerLifeStats pls = this.member.getPlayer().getLifeStats();
      writeD(buf, pls.getMaxHp());
      writeD(buf, pls.getCurrentHp());
      writeD(buf, pls.getMaxMp());
      writeD(buf, pls.getCurrentMp());
      writeD(buf, pls.getMaxFp());
      writeD(buf, pls.getCurrentFp());
    }
    else {
      
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
    } 
    writeD(buf, wp.getMapId());
    writeD(buf, wp.getMapId());
    writeF(buf, wp.getX());
    writeF(buf, wp.getY());
    writeF(buf, wp.getZ());
    writeC(buf, pcd.getPlayerClass().getClassId());
    writeC(buf, pcd.getGender().getGenderId());
    writeC(buf, pcd.getLevel());
    writeC(buf, this.event.getId());
    writeH(buf, 0);
    switch (this.event) {








      
      case ENTER:
      case UPDATE:
      case RECONNECT:
      case MEMBER_GROUP_CHANGE:
      case APPOINT_VICE_CAPTAIN:
      case DEMOTE_VICE_CAPTAIN:
      case APPOINT_CAPTAIN:
        writeS(buf, pcd.getName());
        writeD(buf, 0);
        
        if (this.member.isOnline()) {
          
          List<Effect> abnormalEffects = this.member.getPlayer().getEffectController().getAbnormalEffects();
          writeH(buf, abnormalEffects.size());
          for (Effect effect : abnormalEffects) {
            
            writeD(buf, effect.getEffectorId());
            writeH(buf, effect.getSkillId());
            writeC(buf, effect.getSkillLevel());
            writeC(buf, effect.getTargetSlot());
            writeD(buf, effect.getElapsedTime());
          } 
          
          break;
        } 
        writeH(buf, 0);
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ALLIANCE_MEMBER_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
