package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.tribe.AggroRelations;
import com.aionemu.gameserver.model.templates.tribe.FriendlyRelations;
import com.aionemu.gameserver.model.templates.tribe.HostileRelations;
import com.aionemu.gameserver.model.templates.tribe.NeutralRelations;
import com.aionemu.gameserver.model.templates.tribe.SupportRelations;
import com.aionemu.gameserver.model.templates.tribe.Tribe;
import gnu.trove.THashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tribe_relations")
@XmlAccessorType(XmlAccessType.FIELD)
public class TribeRelationsData {
  @XmlElement(name = "tribe", required = true)
  protected List<Tribe> tribeList;
  protected THashMap<String, Tribe> tribeNameMap = new THashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    for (Tribe tribe : this.tribeList) {
      this.tribeNameMap.put(tribe.getName(), tribe);
    }
    this.tribeList = null;
  }

  public int size() {
    return this.tribeNameMap.size();
  }

  public boolean hasAggressiveRelations(String tribeName) {
    Tribe tribe = (Tribe) this.tribeNameMap.get(tribeName);
    if (tribe == null)
      return false;
    AggroRelations aggroRelations = tribe.getAggroRelations();
    return (aggroRelations != null && !aggroRelations.getTo().isEmpty());
  }

  public boolean hasHostileRelations(String tribeName) {
    Tribe tribe = (Tribe) this.tribeNameMap.get(tribeName);
    if (tribe == null)
      return false;
    HostileRelations hostileRelations = tribe.getHostileRelations();
    return (hostileRelations != null && !hostileRelations.getTo().isEmpty());
  }

  public boolean isAggressiveRelation(String tribeName1, String tribeName2) {
    Tribe tribe1 = (Tribe) this.tribeNameMap.get(tribeName1);
    if (tribe1 == null)
      return false;
    AggroRelations aggroRelations = tribe1.getAggroRelations();
    if (aggroRelations == null) {
      return false;
    }
    return aggroRelations.getTo().contains(tribeName2);
  }

  public boolean isSupportRelation(String tribeName1, String tribeName2) {
    Tribe tribe1 = (Tribe) this.tribeNameMap.get(tribeName1);
    if (tribe1 == null)
      return false;
    SupportRelations supportRelations = tribe1.getSupportRelations();
    if (supportRelations == null)
      return false;
    return supportRelations.getTo().contains(tribeName2);
  }

  public boolean isFriendlyRelation(String tribeName1, String tribeName2) {
    Tribe tribe1 = (Tribe) this.tribeNameMap.get(tribeName1);
    if (tribe1 == null)
      return false;
    FriendlyRelations friendlyRelations = tribe1.getFriendlyRelations();
    if (friendlyRelations == null)
      return false;
    return friendlyRelations.getTo().contains(tribeName2);
  }

  public boolean isNeutralRelation(String tribeName1, String tribeName2) {
    Tribe tribe1 = (Tribe) this.tribeNameMap.get(tribeName1);
    if (tribe1 == null)
      return false;
    NeutralRelations neutralRelations = tribe1.getNeutralRelations();
    if (neutralRelations == null)
      return false;
    return neutralRelations.getTo().contains(tribeName2);
  }

  public boolean isHostileRelation(String tribeName1, String tribeName2) {
    Tribe tribe1 = (Tribe) this.tribeNameMap.get(tribeName1);
    if (tribe1 == null)
      return false;
    HostileRelations hostileRelations = tribe1.getHostileRelations();
    if (hostileRelations == null)
      return false;
    return hostileRelations.getTo().contains(tribeName2);
  }

  public boolean isGuardDark(String tribeName) {
    Tribe tribe = (Tribe) this.tribeNameMap.get(tribeName);
    if (tribe == null) {
      return false;
    }
    if ("GUARD_DARK".equals(tribe.getName())) {
      return true;
    }
    String baseTribe = tribe.getBase();
    if (baseTribe != null) {
      return isGuardDark(baseTribe);
    }
    return false;
  }

  public boolean isGuardLight(String tribeName) {
    Tribe tribe = (Tribe) this.tribeNameMap.get(tribeName);
    if (tribe == null) {
      return false;
    }
    if ("GUARD".equals(tribe.getName())) {
      return true;
    }
    String baseTribe = tribe.getBase();
    if (baseTribe != null) {
      return isGuardLight(baseTribe);
    }
    return false;
  }
}
