package com.aionemu.gameserver.controllers;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.movement.MovementType;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOVE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_CANCEL;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.model.HealType;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import java.util.concurrent.Future;
import javolution.util.FastMap;























public abstract class CreatureController<T extends Creature>
  extends VisibleObjectController<Creature>
{
  private FastMap<Integer, Future<?>> tasks = (new FastMap()).shared();




  
  public void notSee(VisibleObject object, boolean isOutOfRange) {
    super.notSee(object, isOutOfRange);
    if (object == getOwner().getTarget()) {
      
      getOwner().setTarget(null);
      PacketSendUtility.broadcastPacket((VisibleObject)getOwner(), (AionServerPacket)new SM_LOOKATOBJECT((VisibleObject)getOwner()));
    } 
    if (object instanceof Creature) {
      getOwner().getAggroList().remove((Creature)object);
    }
  }



  
  public void onStartMove() {
    getOwner().getObserveController().notifyMoveObservers();
  }






  
  public void onMove() {}





  
  public void onStopMove() {}





  
  public void onDie(Creature lastAttacker) {
    getOwner().setCasting(null);
    getOwner().getEffectController().removeAllEffects();
    getOwner().getMoveController().stop();
    getOwner().setState(CreatureState.DEAD);
  }





  
  public void onRespawn() {
    getOwner().unsetState(CreatureState.DEAD);
    getOwner().getAggroList().clear();
  }




  
  public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
    Skill skill = getOwner().getCastingSkill();
    if (skill != null && skill.getSkillTemplate().getCancelRate() > 0) {
      
      int cancelRate = skill.getSkillTemplate().getCancelRate();
      int conc = getOwner().getGameStats().getCurrentStat(StatEnum.CONCENTRATION) / 10;
      float maxHp = getOwner().getGameStats().getCurrentStat(StatEnum.MAXHP);
      float cancel = (cancelRate - conc) + damage / maxHp * 50.0F;
      if (Rnd.get(100) < cancel)
        cancelCurrentSkill(); 
    } 
    getOwner().getObserveController().notifyAttackedObservers(creature);
    getOwner().getAggroList().addDamage(creature, damage);
  }




  
  public final void onAttack(Creature creature, int damage) {
    onAttack(creature, 0, SM_ATTACK_STATUS.TYPE.REGULAR, damage);
  }






  
  public void onRestore(HealType hopType, int value) {
    switch (hopType) {
      
      case HP:
        getOwner().getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.HP, value);
        break;
      case MP:
        getOwner().getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.MP, value);
        break;
      case FP:
        getOwner().getLifeStats().increaseFp(value);
        break;
    } 
  }






  
  public void doReward() {}






  
  public void onDialogRequest(Player player) {}






  
  public void attackTarget(Creature target) {
    getOwner().getObserveController().notifyAttackObservers(target);
  }




  
  public void stopMoving() {
    Creature owner = getOwner();
    World.getInstance().updatePosition((VisibleObject)owner, owner.getX(), owner.getY(), owner.getZ(), owner.getHeading());
    PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_MOVE(owner.getObjectId(), owner.getX(), owner.getY(), owner.getZ(), owner.getHeading(), MovementType.MOVEMENT_STOP));
  }









  
  public void onDialogSelect(int dialogId, Player player, int questId) {}








  
  public Future<?> getTask(TaskId taskId) {
    return (Future)this.tasks.get(Integer.valueOf(taskId.ordinal()));
  }






  
  public boolean hasTask(TaskId taskId) {
    return this.tasks.containsKey(Integer.valueOf(taskId.ordinal()));
  }





  
  public void cancelTask(TaskId taskId) {
    Future<?> task = (Future)this.tasks.remove(Integer.valueOf(taskId.ordinal()));
    if (task != null)
    {
      task.cancel(false);
    }
  }






  
  public void addTask(TaskId taskId, Future<?> task) {
    cancelTask(taskId);
    this.tasks.put(Integer.valueOf(taskId.ordinal()), task);
  }






  
  public void addNewTask(TaskId taskId, Future<?> task) {
    this.tasks.putIfAbsent(Integer.valueOf(taskId.ordinal()), task);
  }





  
  public void cancelAllTasks() {
    for (Future<?> task : (Iterable<Future<?>>)this.tasks.values()) {
      
      if (task != null)
      {
        task.cancel(true);
      }
    } 
    
    this.tasks = (new FastMap()).shared();
  }


  
  public void delete() {
    cancelAllTasks();
    super.delete();
  }




  
  public void die() {
    getOwner().getLifeStats().reduceHp(getOwner().getLifeStats().getCurrentHp() + 1, null);
  }





  
  public void useSkill(int skillId) {
    Creature creature = getOwner();
    
    Skill skill = SkillEngine.getInstance().getSkill(creature, skillId, 1, creature.getTarget());
    if (skill != null)
    {
      skill.useSkill();
    }
  }






  
  public void broadcastHate(int value) {
    for (VisibleObject visibleObject : getOwner().getKnownList().getKnownObjects().values()) {
      
      if (visibleObject instanceof Creature)
      {
        ((Creature)visibleObject).getAggroList().notifyHate(getOwner(), value);
      }
    } 
  }
  
  public void abortCast() {
    Creature creature = getOwner();
    Skill skill = creature.getCastingSkill();
    if (skill == null)
      return; 
    creature.setCasting(null);
  }




  
  public void cancelCurrentSkill() {
    Creature creature = getOwner();
    Skill castingSkill = creature.getCastingSkill();
    if (castingSkill != null) {
      
      creature.removeSkillCoolDown(castingSkill.getSkillTemplate().getSkillId());
      creature.setCasting(null);
      PacketSendUtility.broadcastPacketAndReceive((VisibleObject)creature, (AionServerPacket)new SM_SKILL_CANCEL(creature, castingSkill.getSkillTemplate().getSkillId()));
    } 
  }
  
  public void createSummon(int npcId, int skillLvl) {}
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\CreatureController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
