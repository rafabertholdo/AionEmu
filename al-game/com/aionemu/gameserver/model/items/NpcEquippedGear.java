package com.aionemu.gameserver.model.items;

import com.aionemu.gameserver.dataholders.loadingutils.adapters.NpcEquipmentList;
import com.aionemu.gameserver.dataholders.loadingutils.adapters.NpcEquippedGearAdapter;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javolution.util.FastList;

@XmlJavaTypeAdapter(NpcEquippedGearAdapter.class)
public class NpcEquippedGear {
  private Map<ItemSlot, ItemTemplate> items;
  private short mask;
  private NpcEquipmentList v;

  public NpcEquippedGear(NpcEquipmentList v) {
    this.v = v;
  }

  public short getItemsMask() {
    if (this.items == null) {
      init();
    }
    return this.mask;
  }

  public Collection<Map.Entry<ItemSlot, ItemTemplate>> getItems() {
    if (this.items == null) {
      init();
    }
    return this.items.entrySet();
  }

  public void init() {
    synchronized (this) {

      if (this.items == null) {

        this.items = new TreeMap<ItemSlot, ItemTemplate>();
        for (ItemTemplate item : this.v.items) {

          FastList<ItemSlot> fastList = ItemSlot.getSlotsFor(item.getItemSlot());
          for (ItemSlot itemSlot : fastList) {

            if (this.items.get(itemSlot) == null) {

              this.items.put(itemSlot, item);
              this.mask = (short) (this.mask | itemSlot.getSlotIdMask());
              break;
            }
          }
        }
      }
      this.v = null;
    }
  }

  public ItemTemplate getItem(ItemSlot itemSlot) {
    return (this.items != null) ? this.items.get(itemSlot) : null;
  }
}
