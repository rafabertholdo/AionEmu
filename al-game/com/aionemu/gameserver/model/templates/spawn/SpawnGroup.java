package com.aionemu.gameserver.model.templates.spawn;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.spawnengine.SpawnHandlerType;
import gnu.trove.TIntIntHashMap;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.apache.log4j.Logger;



































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spawn")
public class SpawnGroup
{
  @XmlAttribute(name = "time")
  private SpawnTime spawnTime;
  @XmlAttribute(name = "anchor")
  private String anchor;
  @XmlAttribute(name = "handler")
  private SpawnHandlerType handler;
  @XmlAttribute(name = "interval")
  private int interval;
  @XmlAttribute(name = "pool")
  private int pool;
  @XmlAttribute(name = "npcid")
  private int npcid;
  @XmlAttribute(name = "map")
  private int mapid;
  @XmlAttribute(name = "rw")
  private int randomWalk;
  @XmlElement(name = "object")
  private List<SpawnTemplate> objects;
  @XmlTransient
  private TIntIntHashMap lastSpawnedTemplate = new TIntIntHashMap();








  
  public SpawnGroup() {}








  
  public SpawnGroup(int mapid, int npcid, int interval, int pool) {
    this.mapid = mapid;
    this.npcid = npcid;
    this.interval = interval;
    this.pool = pool;
  }

  
  void afterUnmarshal(Unmarshaller u, Object parent) {
    if (this.objects != null && this.pool > this.objects.size()) {
      
      Logger.getLogger(SpawnGroup.class).warn("Incorrect pool value for spawn group. MapId:" + this.mapid + " Npc: " + this.npcid);
      
      this.pool = this.objects.size();
    } 
    
    if (this.randomWalk != 0)
    {
      for (SpawnTemplate spawn : this.objects)
      {
        spawn.setRandomWalkNr(this.randomWalk);
      }
    }
  }




  
  public int getMapid() {
    return this.mapid;
  }






  
  public int getNpcid() {
    return this.npcid;
  }




  
  public int getInterval() {
    return this.interval;
  }




  
  public int getPool() {
    return this.pool;
  }




  
  public List<SpawnTemplate> getObjects() {
    if (this.objects == null) {
      this.objects = new ArrayList<SpawnTemplate>();
    }
    return this.objects;
  }




  
  public SpawnHandlerType getHandler() {
    return this.handler;
  }




  
  public String getAnchor() {
    return this.anchor;
  }




  
  public SpawnTime getSpawnTime() {
    return this.spawnTime;
  }







  
  public SpawnTemplate getNextAvailableTemplate(int instance) {
    for (int i = 0; i < getObjects().size(); ) {
      
      Integer lastSpawnCounter = Integer.valueOf(this.lastSpawnedTemplate.get(instance));
      int nextSpawnCounter = (lastSpawnCounter == null) ? 0 : (lastSpawnCounter.intValue() + 1);
      
      if (nextSpawnCounter >= this.objects.size()) {
        nextSpawnCounter = 0;
      }
      SpawnTemplate nextSpawn = this.objects.get(nextSpawnCounter);
      if (nextSpawn.isSpawned(instance)) {
        i++; continue;
      } 
      this.lastSpawnedTemplate.put(instance, nextSpawnCounter);
      return nextSpawn;
    } 
    return null;
  }

  
  public int size() {
    return getObjects().size();
  }




  
  public SpawnTemplate getNextRandomTemplate() {
    return this.objects.get(Rnd.get(0, size() - 1));
  }






  
  public void resetLastSpawnCounter(int instanceIndex) {
    if (this.lastSpawnedTemplate.containsKey(instanceIndex)) {
      this.lastSpawnedTemplate.remove(instanceIndex);
    }
  }






  
  public boolean isFullPool() {
    return (this.pool == this.objects.size());
  }




  
  public synchronized void exchangeSpawn(VisibleObject visibleObject) {
    if (isFullPool()) {
      return;
    }
    int instanceId = visibleObject.getInstanceId();
    SpawnTemplate nextSpawn = getNextAvailableTemplate(instanceId);
    if (nextSpawn != null)
      visibleObject.setSpawn(nextSpawn); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\spawn\SpawnGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
