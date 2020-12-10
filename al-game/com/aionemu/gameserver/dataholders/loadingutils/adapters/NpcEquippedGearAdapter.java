package com.aionemu.gameserver.dataholders.loadingutils.adapters;

import com.aionemu.gameserver.model.items.NpcEquippedGear;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NpcEquippedGearAdapter extends XmlAdapter<NpcEquipmentList, NpcEquippedGear> {
  public NpcEquipmentList marshal(NpcEquippedGear v) throws Exception {
    return null;
  }

  public NpcEquippedGear unmarshal(NpcEquipmentList v) throws Exception {
    return new NpcEquippedGear(v);
  }
}
