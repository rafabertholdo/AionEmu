package com.aionemu.gameserver.model.drop;

import gnu.trove.TIntObjectHashMap;
import java.util.HashSet;
import java.util.Set;

public class DropList {
  private TIntObjectHashMap<Set<DropTemplate>> templatesMap = new TIntObjectHashMap();

  public void addDropTemplate(int mobId, DropTemplate dropTemplate) {
    Set<DropTemplate> dropTemplates = (Set<DropTemplate>) this.templatesMap.get(mobId);
    if (dropTemplates == null) {

      dropTemplates = new HashSet<DropTemplate>();
      this.templatesMap.put(mobId, dropTemplates);
    }
    dropTemplates.add(dropTemplate);
  }

  public Set<DropTemplate> getDropsFor(int mobId) {
    return (Set<DropTemplate>) this.templatesMap.get(mobId);
  }

  public int getSize() {
    return this.templatesMap.size();
  }
}
