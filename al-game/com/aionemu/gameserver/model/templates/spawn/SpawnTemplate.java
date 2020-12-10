package com.aionemu.gameserver.model.templates.spawn;

import java.util.BitSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;



























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "object")
public class SpawnTemplate
{
  @XmlTransient
  private SpawnGroup spawnGroup;
  @XmlAttribute(name = "rw")
  private int randomWalk;
  @XmlAttribute(name = "w")
  private int walkerId;
  @XmlAttribute(name = "h")
  private byte heading;
  @XmlAttribute(name = "z")
  private float z;
  @XmlAttribute(name = "y")
  private float y;
  @XmlAttribute(name = "x")
  private float x;
  @XmlAttribute(name = "staticid")
  private int staticid;
  @XmlAttribute(name = "fly")
  private int npcfly;
  @XmlTransient
  private BitSet spawnState = new BitSet();
  @XmlTransient
  private BitSet noRespawn = new BitSet();
  @XmlTransient
  private BitSet restingState = new BitSet();









  
  public SpawnTemplate() {}









  
  public SpawnTemplate(float x, float y, float z, byte heading, int walkerId, int randomWalk, int npcfly) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.heading = heading;
    this.walkerId = walkerId;
    this.randomWalk = randomWalk;
    this.npcfly = npcfly;
  }

  
  public int getWorldId() {
    return this.spawnGroup.getMapid();
  }

  
  public float getX() {
    return this.x;
  }

  
  public float getY() {
    return this.y;
  }

  
  public float getZ() {
    return this.z;
  }

  
  public byte getHeading() {
    return this.heading;
  }

  
  public int getWalkerId() {
    return this.walkerId;
  }




  
  protected void setRandomWalkNr(int rw) {
    if (this.randomWalk == 0)
    {
      this.randomWalk = rw;
    }
  }

  
  public int getRandomWalkNr() {
    return this.randomWalk;
  }

  
  public boolean hasRandomWalk() {
    return (this.randomWalk > 0);
  }

  
  public int getNpcFlyState() {
    return this.npcfly;
  }




  
  public SpawnGroup getSpawnGroup() {
    return this.spawnGroup;
  }




  
  public void setSpawnGroup(SpawnGroup spawnGroup) {
    this.spawnGroup = spawnGroup;
  }




  
  public boolean isResting(int instance) {
    return this.restingState.get(instance);
  }




  
  public void setResting(boolean isResting, int instance) {
    this.restingState.set(instance, isResting);
  }




  
  public boolean isSpawned(int instance) {
    return this.spawnState.get(instance);
  }




  
  public void setSpawned(boolean isSpawned, int instance) {
    this.spawnState.set(instance, isSpawned);
  }






  
  public boolean isNoRespawn(int instance) {
    return this.noRespawn.get(instance);
  }




  
  public void setNoRespawn(boolean noRespawn, int instance) {
    this.noRespawn.set(instance, noRespawn);
  }




  
  public int getStaticid() {
    return this.staticid;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\spawn\SpawnTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
