package com.aionemu.gameserver.model.templates.spawn;

import com.aionemu.gameserver.utils.gametime.DayTime;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SpawnTime")
@XmlEnum
public enum SpawnTime {
  DAY, NIGHT;

  public boolean isAllowedDuring(DayTime dayTime) {
    switch (this) {

      case DAY:
        return (dayTime == DayTime.AFTERNOON || dayTime == DayTime.MORNING || dayTime == DayTime.EVENING);

      case NIGHT:
        return (dayTime == DayTime.NIGHT);
    }
    return true;
  }
}
