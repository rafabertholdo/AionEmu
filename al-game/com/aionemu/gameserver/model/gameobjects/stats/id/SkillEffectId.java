package com.aionemu.gameserver.model.gameobjects.stats.id;

import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;





















public class SkillEffectId
  extends StatEffectId
{
  private int effectId;
  private int effectOrder;
  
  private SkillEffectId(int skillId, int effectId, int effectOrder) {
    super(skillId, StatEffectType.SKILL_EFFECT);
    this.effectId = effectId;
    this.effectOrder = effectOrder;
  }

  
  public static SkillEffectId getInstance(int skillId, int effectId, int effectOrder) {
    return new SkillEffectId(skillId, effectId, effectOrder);
  }


  
  public boolean equals(Object o) {
    boolean result = super.equals(o);
    result = (result && o != null);
    result = (result && o instanceof SkillEffectId);
    result = (result && ((SkillEffectId)o).effectId == this.effectId);
    result = (result && ((SkillEffectId)o).effectOrder == this.effectOrder);
    return result;
  }


  
  public int compareTo(StatEffectId o) {
    int result = super.compareTo(o);
    if (result == 0)
    {
      if (o instanceof SkillEffectId) {
        
        result = this.effectId - ((SkillEffectId)o).effectId;
        if (result == 0)
          result = this.effectOrder - ((SkillEffectId)o).effectOrder; 
      } 
    }
    return result;
  }


  
  public String toString() {
    String str = super.toString() + ",effectId:" + this.effectId + ",effectOrder:" + this.effectOrder;
    return str;
  }




  
  public int getEffectId() {
    return this.effectId;
  }




  
  public int getEffectOrder() {
    return this.effectOrder;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\SkillEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
