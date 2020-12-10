package com.aionemu.gameserver.model.templates.tribe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Neutral")
public class NeutralRelations {
  @XmlElement(required = true)
  protected List<String> to;

  public List<String> getTo() {
    if (this.to == null) {
      this.to = new ArrayList<String>();
    }
    return this.to;
  }
}
