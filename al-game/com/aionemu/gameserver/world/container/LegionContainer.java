package com.aionemu.gameserver.world.container;

import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
import java.util.Collection;
import java.util.Map;
import javolution.util.FastMap;

public class LegionContainer {
  private final Map<Integer, Legion> legionsById = (Map<Integer, Legion>) (new FastMap()).shared();

  private final Map<String, Legion> legionsByName = (Map<String, Legion>) (new FastMap()).shared();

  public void add(Legion legion) {
    if (this.legionsById.put(Integer.valueOf(legion.getLegionId()), legion) != null)
      throw new DuplicateAionObjectException();
    if (this.legionsByName.put(legion.getLegionName().toLowerCase(), legion) != null) {
      throw new DuplicateAionObjectException();
    }
  }

  public void remove(Legion legion) {
    this.legionsById.remove(Integer.valueOf(legion.getLegionId()));
    this.legionsByName.remove(legion.getLegionName().toLowerCase());
  }

  public Legion get(int legionId) {
    return this.legionsById.get(Integer.valueOf(legionId));
  }

  public Legion get(String name) {
    return this.legionsByName.get(name.toLowerCase());
  }

  public boolean contains(int legionId) {
    return this.legionsById.containsKey(Integer.valueOf(legionId));
  }

  public boolean contains(String name) {
    return this.legionsByName.containsKey(name.toLowerCase());
  }

  public Collection<Legion> getLegions() {
    return this.legionsById.values();
  }
}
