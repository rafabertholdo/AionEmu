package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.TradeListTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "npc_trade_list")
@XmlAccessorType(XmlAccessType.FIELD)
public class TradeListData {
  @XmlElement(name = "tradelist_template")
  private List<TradeListTemplate> tlist;
  private TIntObjectHashMap<TradeListTemplate> npctlistData = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (TradeListTemplate npc : this.tlist) {
      this.npctlistData.put(npc.getNpcId(), npc);
    }
  }

  public int size() {
    return this.npctlistData.size();
  }

  public TradeListTemplate getTradeListTemplate(int id) {
    return (TradeListTemplate) this.npctlistData.get(id);
  }
}
