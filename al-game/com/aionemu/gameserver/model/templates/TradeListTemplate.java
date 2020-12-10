package com.aionemu.gameserver.model.templates;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "tradelist_template")
@XmlAccessorType(XmlAccessType.NONE)
public class TradeListTemplate {
  @XmlAttribute(name = "npc_id", required = true)
  private int npcId;
  @XmlAttribute(name = "name", required = true)
  private String name = "";

  @XmlAttribute(name = "count", required = true)
  private int Count = 0;

  @XmlAttribute(name = "abyss")
  private boolean abyss;

  @XmlElement(name = "tradelist")
  protected List<TradeTab> tradeTablist;

  @XmlAttribute(name = "sell_price_rate")
  protected int sellPriceRate = 100;

  public List<TradeTab> getTradeTablist() {
    if (this.tradeTablist == null) {
      this.tradeTablist = new ArrayList<TradeTab>();
    }
    return this.tradeTablist;
  }

  public String getName() {
    return this.name;
  }

  public int getNpcId() {
    return this.npcId;
  }

  public int getCount() {
    return this.Count;
  }

  public boolean isAbyss() {
    return this.abyss;
  }

  public int getSellPriceRate() {
    return this.sellPriceRate;
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "Tradelist")
  public static class TradeTab {
    @XmlAttribute
    protected int id;

    public int getId() {
      return this.id;
    }
  }
}
