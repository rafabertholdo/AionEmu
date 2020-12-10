package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.controllers.CreatureController;
import com.aionemu.gameserver.controllers.SummonController;
import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
import com.aionemu.gameserver.model.gameobjects.stats.SummonGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.SummonLifeStats;
import com.aionemu.gameserver.model.templates.NpcTemplate;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.model.templates.stats.SummonStatsTemplate;
import com.aionemu.gameserver.world.WorldPosition;


















public class Summon
  extends Creature
{
  private Player master;
  private SummonMode mode;
  private byte level;
  
  public enum SummonMode
  {
    ATTACK(0),
    GUARD(1),
    REST(2),
    RELEASE(3);
    
    private int id;

    
    SummonMode(int id) {
      this.id = id;
    }




    
    public int getId() {
      return this.id;
    }
  }










  
  public Summon(int objId, CreatureController<? extends Creature> controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate, SummonStatsTemplate statsTemplate, byte level) {
    super(objId, controller, spawnTemplate, objectTemplate, new WorldPosition());
    
    controller.setOwner(this);
    this.level = level;
    setGameStats((CreatureGameStats<? extends Creature>)new SummonGameStats(this, statsTemplate));
    setLifeStats((CreatureLifeStats<? extends Creature>)new SummonLifeStats(this));
    
    this.mode = SummonMode.GUARD;
  }





  
  public Player getMaster() {
    return this.master;
  }





  
  public void setMaster(Player master) {
    this.master = master;
  }


  
  public String getName() {
    return this.objectTemplate.getName();
  }





  
  public byte getLevel() {
    return this.level;
  }



  
  public void initializeAi() {}



  
  public NpcTemplate getObjectTemplate() {
    return (NpcTemplate)super.getObjectTemplate();
  }

  
  public int getNpcId() {
    return getObjectTemplate().getTemplateId();
  }

  
  public int getNameId() {
    return getObjectTemplate().getNameId();
  }





  
  public NpcObjectType getNpcObjectType() {
    return NpcObjectType.SUMMON;
  }


  
  public SummonController getController() {
    return (SummonController)super.getController();
  }




  
  public SummonMode getMode() {
    return this.mode;
  }




  
  public void setMode(SummonMode mode) {
    this.mode = mode;
  }


  
  protected boolean isEnemyNpc(Npc visibleObject) {
    return this.master.isEnemyNpc(visibleObject);
  }


  
  protected boolean isEnemyPlayer(Player visibleObject) {
    return this.master.isEnemyPlayer(visibleObject);
  }


  
  protected boolean isEnemySummon(Summon summon) {
    return this.master.isEnemySummon(summon);
  }


  
  public String getTribe() {
    return this.master.getTribe();
  }


  
  public boolean isAggressiveTo(Creature creature) {
    return creature.isAggroFrom(this);
  }


  
  public boolean isAggroFrom(Npc npc) {
    if (getMaster() == null) {
      return false;
    }
    return getMaster().isAggroFrom(npc);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Summon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
