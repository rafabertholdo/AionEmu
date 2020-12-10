package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.templates.stats.SummonStatsTemplate;



























public class SummonGameStats
  extends CreatureGameStats<Summon>
{
  public SummonGameStats(Summon owner, SummonStatsTemplate statsTemplate) {
    super(owner);
    initStat(StatEnum.MAXHP, statsTemplate.getMaxHp());
    initStat(StatEnum.MAXMP, statsTemplate.getMaxMp());
    initStat(StatEnum.MAIN_HAND_POWER, statsTemplate.getMainHandAttack());
    initStat(StatEnum.PHYSICAL_DEFENSE, statsTemplate.getPdefense());
    initStat(StatEnum.MAGICAL_RESIST, statsTemplate.getMresist());
    initStat(StatEnum.ATTACK_SPEED, 2000);
    initStat(StatEnum.SPEED, Math.round(statsTemplate.getRunSpeed() * 1000.0F));
    initStat(StatEnum.REGEN_HP, owner.getLevel() + 3);
    initStat(StatEnum.KNOWLEDGE, 100);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\SummonGameStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
