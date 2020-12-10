package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
import com.aionemu.gameserver.model.group.GroupEvent;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.world.WorldPosition;
import java.nio.ByteBuffer;
import java.util.List;

public class SM_GROUP_MEMBER_INFO extends AionServerPacket {
  private PlayerGroup group;
  private Player player;
  private GroupEvent event;

  public SM_GROUP_MEMBER_INFO(PlayerGroup group, Player player, GroupEvent event) {
    this.group = group;
    this.player = player;
    this.event = event;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    PlayerLifeStats pls = this.player.getLifeStats();
    PlayerCommonData pcd = this.player.getCommonData();
    WorldPosition wp = pcd.getPosition();

    writeD(buf, this.group.getGroupId());
    writeD(buf, this.player.getObjectId());
    writeD(buf, pls.getMaxHp());
    writeD(buf, pls.getCurrentHp());
    writeD(buf, pls.getMaxMp());
    writeD(buf, pls.getCurrentMp());
    writeD(buf, pls.getMaxFp());
    writeD(buf, pls.getCurrentFp());
    writeD(buf, wp.getMapId());
    writeD(buf, wp.getMapId());
    writeF(buf, wp.getX());
    writeF(buf, wp.getY());
    writeF(buf, wp.getZ());
    writeC(buf, pcd.getPlayerClass().getClassId());
    writeC(buf, pcd.getGender().getGenderId());
    writeC(buf, pcd.getLevel());
    writeC(buf, this.event.getId());
    writeH(buf, 1);
    if (this.event == GroupEvent.MOVEMENT) {
      return;
    }

    writeS(buf, pcd.getName());
    writeH(buf, 0);
    writeH(buf, 0);

    List<Effect> abnormalEffects = this.player.getEffectController().getAbnormalEffects();
    writeH(buf, abnormalEffects.size());
    for (Effect effect : abnormalEffects) {

      writeD(buf, effect.getEffectorId());
      writeH(buf, effect.getSkillId());
      writeC(buf, effect.getSkillLevel());
      writeC(buf, effect.getTargetSlot());
      writeD(buf, effect.getElapsedTime());
    }
    writeD(buf, 9719);
  }
}
