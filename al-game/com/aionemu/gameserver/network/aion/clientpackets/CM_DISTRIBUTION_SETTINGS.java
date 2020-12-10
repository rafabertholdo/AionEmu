package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.LootDistribution;
import com.aionemu.gameserver.model.group.LootGroupRules;
import com.aionemu.gameserver.model.group.LootRuleType;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;

public class CM_DISTRIBUTION_SETTINGS extends AionClientPacket {
  private LootRuleType lootrules;
  private LootDistribution autodistribution;
  private int common_item_above;
  private int superior_item_above;
  private int heroic_item_above;
  private int fabled_item_above;
  private int ethernal_item_above;
  private int over_ethernal;
  private int over_over_ethernal;

  public CM_DISTRIBUTION_SETTINGS(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    switch (readD()) {

      case 0:
        this.lootrules = LootRuleType.FREEFORALL;
        break;
      case 1:
        this.lootrules = LootRuleType.ROUNDROBIN;
        break;
      case 2:
        this.lootrules = LootRuleType.LEADER;
        break;
      default:
        this.lootrules = LootRuleType.FREEFORALL;
        break;
    }

    switch (readD()) {

      case 0:
        this.autodistribution = LootDistribution.NORMAL;
        break;
      case 2:
        this.autodistribution = LootDistribution.ROLL_DICE;
        break;
      case 3:
        this.autodistribution = LootDistribution.BID;
        break;
      default:
        this.autodistribution = LootDistribution.NORMAL;
        break;
    }

    this.common_item_above = readD();
    this.superior_item_above = readD();
    this.heroic_item_above = readD();
    this.fabled_item_above = readD();
    this.ethernal_item_above = readD();
    this.over_ethernal = readD();
    this.over_over_ethernal = readD();
  }

  protected void runImpl() {
    Player leader = ((AionConnection) getConnection()).getActivePlayer();
    if (leader != null) {

      PlayerGroup pg = leader.getPlayerGroup();
      if (pg != null)
        pg.setLootGroupRules(new LootGroupRules(this.lootrules, this.autodistribution, this.common_item_above,
            this.superior_item_above, this.heroic_item_above, this.fabled_item_above, this.ethernal_item_above,
            this.over_ethernal, this.over_over_ethernal));
    }
  }
}
