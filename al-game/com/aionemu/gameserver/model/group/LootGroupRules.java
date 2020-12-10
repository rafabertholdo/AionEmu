package com.aionemu.gameserver.model.group;

import com.aionemu.gameserver.model.templates.item.ItemQuality;






















public class LootGroupRules
{
  private LootRuleType lootRule;
  private LootDistribution autodistribution;
  private int common_item_above;
  private int superior_item_above;
  private int heroic_item_above;
  private int fabled_item_above;
  private int ethernal_item_above;
  private int over_ethernal;
  private int over_over_ethernal;
  
  public LootGroupRules() {
    this.lootRule = LootRuleType.ROUNDROBIN;
    this.autodistribution = LootDistribution.NORMAL;
    this.common_item_above = 0;
    this.superior_item_above = 2;
    this.heroic_item_above = 2;
    this.fabled_item_above = 2;
    this.ethernal_item_above = 2;
    this.over_ethernal = 2;
    this.over_over_ethernal = 0;
  }



  
  public LootGroupRules(LootRuleType lootRule, LootDistribution autodistribution, int commonItemAbove, int superiorItemAbove, int heroicItemAbove, int fabledItemAbove, int ethernalItemAbove, int overEthernal, int overOverEthernal) {
    this.lootRule = lootRule;
    this.autodistribution = autodistribution;
    this.common_item_above = commonItemAbove;
    this.superior_item_above = superiorItemAbove;
    this.heroic_item_above = heroicItemAbove;
    this.fabled_item_above = fabledItemAbove;
    this.ethernal_item_above = ethernalItemAbove;
    this.over_ethernal = overEthernal;
    this.over_over_ethernal = overOverEthernal;
  }





  
  public int getQualityRule(ItemQuality quality) {
    switch (quality) {
      
      case COMMON:
        return this.common_item_above;
      case RARE:
        return this.superior_item_above;
      case LEGEND:
        return this.heroic_item_above;
      case UNIQUE:
        return this.fabled_item_above;
      case EPIC:
        return this.ethernal_item_above;
      case MYTHIC:
        return this.over_ethernal;
    } 
    return 0;
  }




  
  public LootRuleType getLootRule() {
    return this.lootRule;
  }




  
  public LootDistribution getAutodistribution() {
    return this.autodistribution;
  }




  
  public int getCommonItemAbove() {
    return this.common_item_above;
  }




  
  public int getSuperiorItemAbove() {
    return this.superior_item_above;
  }




  
  public int getHeroicItemAbove() {
    return this.heroic_item_above;
  }




  
  public int getFabledItemAbove() {
    return this.fabled_item_above;
  }




  
  public int getEthernalItemAbove() {
    return this.ethernal_item_above;
  }




  
  public int getOverEthernal() {
    return this.over_ethernal;
  }




  
  public int getOverOverEthernal() {
    return this.over_over_ethernal;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\LootGroupRules.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
