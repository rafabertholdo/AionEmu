package com.aionemu.gameserver.model.templates.gather;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Materials", propOrder = { "material" })
public class Materials {
  protected List<Material> material;

  public List<Material> getMaterial() {
    if (this.material == null) {
      this.material = new ArrayList<Material>();
    }
    return this.material;
  }
}
