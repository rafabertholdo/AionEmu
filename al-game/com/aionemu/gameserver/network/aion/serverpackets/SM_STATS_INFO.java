package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;
import java.nio.ByteBuffer;
































public class SM_STATS_INFO
  extends AionServerPacket
{
  private Player player;
  private PlayerGameStats pgs;
  private PlayerLifeStats pls;
  private PlayerCommonData pcd;
  
  public SM_STATS_INFO(Player player) {
    this.player = player;
    this.pcd = player.getCommonData();
    this.pgs = player.getGameStats();
    this.pls = player.getLifeStats();
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.player.getObjectId());
    writeD(buf, GameTimeManager.getGameTime().getTime());
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.POWER));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.HEALTH));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.ACCURACY));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.AGILITY));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.KNOWLEDGE));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.WILL));
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.WATER_RESISTANCE));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.WIND_RESISTANCE));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.EARTH_RESISTANCE));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.FIRE_RESISTANCE));
    writeH(buf, 0);
    writeH(buf, 0);
    
    writeH(buf, this.player.getLevel());

    
    writeH(buf, 0);
    writeH(buf, 0);
    writeH(buf, 0);
    
    writeQ(buf, this.pcd.getExpNeed());
    writeQ(buf, this.pcd.getExpRecoverable());
    writeQ(buf, this.pcd.getExpShown());
    
    writeD(buf, 0);
    writeD(buf, this.pgs.getCurrentStat(StatEnum.MAXHP));
    writeD(buf, this.pls.getCurrentHp());
    
    writeD(buf, this.pgs.getCurrentStat(StatEnum.MAXMP));
    writeD(buf, this.pls.getCurrentMp());
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAXDP));
    writeH(buf, this.pcd.getDp());
    
    writeD(buf, this.pgs.getCurrentStat(StatEnum.FLY_TIME));
    
    writeD(buf, this.pls.getCurrentFp());
    
    writeC(buf, this.player.getFlyState());
    writeC(buf, 0);
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAIN_HAND_POWER));
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.OFF_HAND_POWER));
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.PHYSICAL_DEFENSE));
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAIN_HAND_POWER));
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAGICAL_RESIST));
    
    writeF(buf, this.pgs.getCurrentStat(StatEnum.ATTACK_RANGE) / 1000.0F);
    writeH(buf, this.pgs.getCurrentStat(StatEnum.ATTACK_SPEED));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.EVASION));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.PARRY));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.BLOCK));
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAIN_HAND_CRITICAL));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.OFF_HAND_CRITICAL));
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAIN_HAND_ACCURACY));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.OFF_HAND_ACCURACY));
    
    writeH(buf, 0);
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAGICAL_ACCURACY));
    writeH(buf, 0);
    writeH(buf, 0);
    
    writeH(buf, 0);
    writeH(buf, 16256);
    writeH(buf, 40);
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAGICAL_ATTACK) + this.pgs.getCurrentStat(StatEnum.BOOST_MAGICAL_SKILL));
    writeH(buf, this.pgs.getCurrentStat(StatEnum.BOOST_HEAL) - 100);
    writeH(buf, this.pgs.getCurrentStat(StatEnum.CRITICAL_RESIST));
    writeH(buf, 0);
    writeH(buf, 0);
    writeH(buf, 0);
    writeH(buf, 20511);
    
    writeD(buf, 27 + this.player.getCubeSize() * 9);
    
    writeD(buf, this.player.getInventory().size());
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, this.pcd.getPlayerClass().getClassId());
    
    writeQ(buf, 0L);
    writeQ(buf, 0L);
    writeQ(buf, 251141L);
    writeQ(buf, 0L);



    
    writeH(buf, this.pgs.getBaseStat(StatEnum.POWER));
    writeH(buf, this.pgs.getBaseStat(StatEnum.HEALTH));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.ACCURACY));
    writeH(buf, this.pgs.getBaseStat(StatEnum.AGILITY));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.KNOWLEDGE));
    writeH(buf, this.pgs.getBaseStat(StatEnum.WILL));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.WATER_RESISTANCE));
    writeH(buf, this.pgs.getBaseStat(StatEnum.WIND_RESISTANCE));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.EARTH_RESISTANCE));
    writeH(buf, this.pgs.getBaseStat(StatEnum.FIRE_RESISTANCE));
    
    writeD(buf, 0);
    
    writeD(buf, this.pgs.getBaseStat(StatEnum.MAXHP));
    
    writeD(buf, this.pgs.getBaseStat(StatEnum.MAXMP));
    
    writeD(buf, this.pgs.getBaseStat(StatEnum.MAXDP));
    writeD(buf, this.pgs.getBaseStat(StatEnum.FLY_TIME));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.MAIN_HAND_POWER));
    writeH(buf, this.pgs.getBaseStat(StatEnum.OFF_HAND_POWER));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.MAIN_HAND_POWER));
    writeH(buf, this.pgs.getBaseStat(StatEnum.PHYSICAL_DEFENSE));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.MAGICAL_RESIST));
    
    writeH(buf, 0);
    
    writeF(buf, this.pgs.getCurrentStat(StatEnum.ATTACK_RANGE) / 1000.0F);
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.EVASION));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.PARRY));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.BLOCK));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.MAIN_HAND_CRITICAL));
    writeH(buf, this.pgs.getBaseStat(StatEnum.OFF_HAND_CRITICAL));
    
    writeH(buf, this.pgs.getCurrentStat(StatEnum.MAGICAL_CRITICAL));
    writeH(buf, 0);
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.MAIN_HAND_ACCURACY));
    writeH(buf, this.pgs.getBaseStat(StatEnum.OFF_HAND_ACCURACY));
    
    writeH(buf, 0);
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.MAGICAL_ACCURACY));
    
    writeH(buf, 0);
    writeH(buf, this.pgs.getBaseStat(StatEnum.MAGICAL_ATTACK) + this.pgs.getBaseStat(StatEnum.BOOST_MAGICAL_SKILL));
    
    writeH(buf, this.pgs.getBaseStat(StatEnum.BOOST_HEAL) - 100);
    writeH(buf, this.pgs.getBaseStat(StatEnum.CRITICAL_RESIST));
    writeH(buf, 0);
    writeH(buf, 0);
    writeH(buf, 0);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_STATS_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
