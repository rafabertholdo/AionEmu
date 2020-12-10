package com.aionemu.gameserver.controllers.attack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javolution.util.FastMap;

public class KillList {
  private static final long DAY_IN_MILLISECONDS = 86400000L;
  private FastMap<Integer, List<Long>> killList = new FastMap();

  public int getKillsFor(int victimId) {
    List<Long> killTimes = (List<Long>) this.killList.get(Integer.valueOf(victimId));

    if (killTimes == null) {
      return 0;
    }
    long now = System.currentTimeMillis();
    int killCount = 0;

    for (Iterator<Long> i = killTimes.iterator(); i.hasNext();) {

      if (now - ((Long) i.next()).longValue() > 86400000L) {

        i.remove();

        continue;
      }
      killCount++;
    }

    return killCount;
  }

  public void addKillFor(int victimId) {
    List<Long> killTimes = (List<Long>) this.killList.get(Integer.valueOf(victimId));
    if (killTimes == null) {

      killTimes = new ArrayList<Long>();
      this.killList.put(Integer.valueOf(victimId), killTimes);
    }

    killTimes.add(Long.valueOf(System.currentTimeMillis()));
  }
}
