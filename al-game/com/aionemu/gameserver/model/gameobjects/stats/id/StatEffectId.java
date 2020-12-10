package com.aionemu.gameserver.model.gameobjects.stats.id;

import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;





















public class StatEffectId
  implements Comparable<StatEffectId>
{
  private int id;
  private StatEffectType type;
  
  protected StatEffectId(int id, StatEffectType type) {
    this.id = id;
    this.type = type;
  }

  
  public static StatEffectId getInstance(int id, StatEffectType type) {
    return new StatEffectId(id, type);
  }


  
  public int hashCode() {
    return this.id;
  }


  
  public boolean equals(Object o) {
    boolean result = true;
    result = (result && o != null);
    result = (result && o instanceof StatEffectId);
    result = (result && ((StatEffectId)o).id == this.id);
    result = (result && ((StatEffectId)o).type.getValue() == this.type.getValue());
    return result;
  }


  
  public int compareTo(StatEffectId o) {
    int result = 0;
    if (o == null) {
      
      result = this.id;
    }
    else {
      
      result = this.type.getValue() - o.type.getValue();
      if (result == 0)
      {
        result = this.id - o.id;
      }
    } 
    return result;
  }


  
  public String toString() {
    String str = "id:" + this.id + ",type:" + this.type;
    return str;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\StatEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
