package com.aionemu.gameserver.itemengine.actions;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemActions")
public class ItemActions {
  @XmlElements({ @XmlElement(name = "skilllearn", type = SkillLearnAction.class),
      @XmlElement(name = "extract", type = ExtractAction.class),
      @XmlElement(name = "skilluse", type = SkillUseAction.class),
      @XmlElement(name = "enchant", type = EnchantItemAction.class),
      @XmlElement(name = "queststart", type = QuestStartAction.class),
      @XmlElement(name = "dye", type = DyeAction.class),
      @XmlElement(name = "craftlearn", type = CraftLearnAction.class),
      @XmlElement(name = "toypetspawn", type = ToyPetSpawnAction.class) })
  protected List<AbstractItemAction> itemActions;

  public List<AbstractItemAction> getItemActions() {
    if (this.itemActions == null) {
      this.itemActions = new ArrayList<AbstractItemAction>();
    }
    return this.itemActions;
  }
}
