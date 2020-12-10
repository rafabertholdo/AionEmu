package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.npcai.AggressiveAi;
import com.aionemu.gameserver.ai.npcai.MonsterAi;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.controllers.CreatureController;
import com.aionemu.gameserver.controllers.MonsterController;
import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;




















public class Monster
  extends Npc
{
  public Monster(int objId, MonsterController controller, SpawnTemplate spawn, VisibleObjectTemplate objectTemplate) {
    super(objId, (NpcController)controller, spawn, objectTemplate);
  }


  
  public MonsterController getController() {
    return (MonsterController)super.getController();
  }


  
  public void initializeAi() {
    if (isAggressive() && !CustomConfig.DISABLE_MOB_AGGRO) {
      this.ai = (AI<? extends Creature>)new AggressiveAi();
    } else {
      this.ai = (AI<? extends Creature>)new MonsterAi();
    } 
    this.ai.setOwner(this);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Monster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
