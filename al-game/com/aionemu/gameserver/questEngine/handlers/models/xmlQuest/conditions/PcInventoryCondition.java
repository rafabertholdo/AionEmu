package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.model.ConditionOperation;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PcInventoryCondition")
public class PcInventoryCondition extends QuestCondition {
  @XmlAttribute(name = "item_id", required = true)
  protected int itemId;
  @XmlAttribute(required = true)
  protected long count;

  public int getItemId() {
    return this.itemId;
  }

  public long getCount() {
    return this.count;
  }

  public boolean doCheck(QuestEnv env) {
    Player player = env.getPlayer();
    long itemCount = player.getInventory().getItemCountByItemId(this.itemId);
    switch (getOp()) {

      case EQUAL:
        return (itemCount == this.count);
      case GREATER:
        return (itemCount > this.count);
      case GREATER_EQUAL:
        return (itemCount >= this.count);
      case LESSER:
        return (itemCount < this.count);
      case LESSER_EQUAL:
        return (itemCount <= this.count);
      case NOT_EQUAL:
        return (itemCount != this.count);
    }
    return false;
  }
}
