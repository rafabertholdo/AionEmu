package com.aionemu.gameserver.model.templates.stats;

import com.aionemu.gameserver.model.gameobjects.stats.modifiers.AddModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.MeanModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.RateModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SetModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SubModifier;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "modifiers")
public class ModifiersTemplate {
  @XmlElements({ @XmlElement(name = "sub", type = SubModifier.class),
      @XmlElement(name = "add", type = AddModifier.class), @XmlElement(name = "rate", type = RateModifier.class),
      @XmlElement(name = "set", type = SetModifier.class), @XmlElement(name = "mean", type = MeanModifier.class) })
  private TreeSet<StatModifier> modifiers;

  public TreeSet<StatModifier> getModifiers() {
    return this.modifiers;
  }
}
