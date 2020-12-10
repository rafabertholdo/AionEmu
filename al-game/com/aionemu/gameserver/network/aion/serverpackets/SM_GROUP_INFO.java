package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.group.LootDistribution;
import com.aionemu.gameserver.model.group.LootGroupRules;
import com.aionemu.gameserver.model.group.LootRuleType;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_GROUP_INFO extends AionServerPacket {
  private int groupid;
  private int leaderid;
  private LootRuleType lootruletype;
  private LootDistribution autodistribution;
  private int common_item_above;
  private int superior_item_above;
  private int heroic_item_above;
  private int fabled_item_above;
  private int ethernal_item_above;
  private int over_ethernal;
  private int over_over_ethernal;

  public SM_GROUP_INFO(PlayerGroup group) {
    this.groupid = group.getGroupId();
    this.leaderid = group.getGroupLeader().getObjectId();

    LootGroupRules lootRules = group.getLootGroupRules();
    this.lootruletype = lootRules.getLootRule();
    this.autodistribution = lootRules.getAutodistribution();
    this.common_item_above = lootRules.getCommonItemAbove();
    this.superior_item_above = lootRules.getSuperiorItemAbove();
    this.heroic_item_above = lootRules.getHeroicItemAbove();
    this.fabled_item_above = lootRules.getFabledItemAbove();
    this.ethernal_item_above = lootRules.getEthernalItemAbove();
    this.over_ethernal = lootRules.getOverEthernal();
    this.over_over_ethernal = lootRules.getOverOverEthernal();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.groupid);
    writeD(buf, this.leaderid);
    writeD(buf, this.lootruletype.getId());
    writeD(buf, this.autodistribution.getId());
    writeD(buf, this.common_item_above);
    writeD(buf, this.superior_item_above);
    writeD(buf, this.heroic_item_above);
    writeD(buf, this.fabled_item_above);
    writeD(buf, this.ethernal_item_above);
    writeD(buf, this.over_ethernal);
    writeD(buf, this.over_over_ethernal);
    writeD(buf, 0);
    writeH(buf, 0);
    writeC(buf, 0);
  }
}
