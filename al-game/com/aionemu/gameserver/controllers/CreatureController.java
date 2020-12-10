/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.controllers.movement.MovementType;
/*     */ import com.aionemu.gameserver.model.TaskId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MOVE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_CANCEL;
/*     */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*     */ import com.aionemu.gameserver.skillengine.model.HealType;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.concurrent.Future;
/*     */ import javolution.util.FastMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CreatureController<T extends Creature>
/*     */   extends VisibleObjectController<Creature>
/*     */ {
/*  49 */   private FastMap<Integer, Future<?>> tasks = (new FastMap()).shared();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notSee(VisibleObject object, boolean isOutOfRange) {
/*  56 */     super.notSee(object, isOutOfRange);
/*  57 */     if (object == getOwner().getTarget()) {
/*     */       
/*  59 */       getOwner().setTarget(null);
/*  60 */       PacketSendUtility.broadcastPacket((VisibleObject)getOwner(), (AionServerPacket)new SM_LOOKATOBJECT((VisibleObject)getOwner()));
/*     */     } 
/*  62 */     if (object instanceof Creature) {
/*  63 */       getOwner().getAggroList().remove((Creature)object);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStartMove() {
/*  71 */     getOwner().getObserveController().notifyMoveObservers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMove() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStopMove() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDie(Creature lastAttacker) {
/*  95 */     getOwner().setCasting(null);
/*  96 */     getOwner().getEffectController().removeAllEffects();
/*  97 */     getOwner().getMoveController().stop();
/*  98 */     getOwner().setState(CreatureState.DEAD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRespawn() {
/* 107 */     getOwner().unsetState(CreatureState.DEAD);
/* 108 */     getOwner().getAggroList().clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
/* 116 */     Skill skill = getOwner().getCastingSkill();
/* 117 */     if (skill != null && skill.getSkillTemplate().getCancelRate() > 0) {
/*     */       
/* 119 */       int cancelRate = skill.getSkillTemplate().getCancelRate();
/* 120 */       int conc = getOwner().getGameStats().getCurrentStat(StatEnum.CONCENTRATION) / 10;
/* 121 */       float maxHp = getOwner().getGameStats().getCurrentStat(StatEnum.MAXHP);
/* 122 */       float cancel = (cancelRate - conc) + damage / maxHp * 50.0F;
/* 123 */       if (Rnd.get(100) < cancel)
/* 124 */         cancelCurrentSkill(); 
/*     */     } 
/* 126 */     getOwner().getObserveController().notifyAttackedObservers(creature);
/* 127 */     getOwner().getAggroList().addDamage(creature, damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void onAttack(Creature creature, int damage) {
/* 135 */     onAttack(creature, 0, SM_ATTACK_STATUS.TYPE.REGULAR, damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRestore(HealType hopType, int value) {
/* 145 */     switch (hopType) {
/*     */       
/*     */       case HP:
/* 148 */         getOwner().getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.HP, value);
/*     */         break;
/*     */       case MP:
/* 151 */         getOwner().getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.MP, value);
/*     */         break;
/*     */       case FP:
/* 154 */         getOwner().getLifeStats().increaseFp(value);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doReward() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogRequest(Player player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackTarget(Creature target) {
/* 182 */     getOwner().getObserveController().notifyAttackObservers(target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopMoving() {
/* 190 */     Creature owner = getOwner();
/* 191 */     World.getInstance().updatePosition((VisibleObject)owner, owner.getX(), owner.getY(), owner.getZ(), owner.getHeading());
/* 192 */     PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_MOVE(owner.getObjectId(), owner.getX(), owner.getY(), owner.getZ(), owner.getHeading(), MovementType.MOVEMENT_STOP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogSelect(int dialogId, Player player, int questId) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> getTask(TaskId taskId) {
/* 215 */     return (Future)this.tasks.get(Integer.valueOf(taskId.ordinal()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTask(TaskId taskId) {
/* 225 */     return this.tasks.containsKey(Integer.valueOf(taskId.ordinal()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelTask(TaskId taskId) {
/* 234 */     Future<?> task = (Future)this.tasks.remove(Integer.valueOf(taskId.ordinal()));
/* 235 */     if (task != null)
/*     */     {
/* 237 */       task.cancel(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTask(TaskId taskId, Future<?> task) {
/* 248 */     cancelTask(taskId);
/* 249 */     this.tasks.put(Integer.valueOf(taskId.ordinal()), task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNewTask(TaskId taskId, Future<?> task) {
/* 259 */     this.tasks.putIfAbsent(Integer.valueOf(taskId.ordinal()), task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelAllTasks() {
/* 268 */     for (Future<?> task : (Iterable<Future<?>>)this.tasks.values()) {
/*     */       
/* 270 */       if (task != null)
/*     */       {
/* 272 */         task.cancel(true);
/*     */       }
/*     */     } 
/*     */     
/* 276 */     this.tasks = (new FastMap()).shared();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete() {
/* 282 */     cancelAllTasks();
/* 283 */     super.delete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void die() {
/* 291 */     getOwner().getLifeStats().reduceHp(getOwner().getLifeStats().getCurrentHp() + 1, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void useSkill(int skillId) {
/* 300 */     Creature creature = getOwner();
/*     */     
/* 302 */     Skill skill = SkillEngine.getInstance().getSkill(creature, skillId, 1, creature.getTarget());
/* 303 */     if (skill != null)
/*     */     {
/* 305 */       skill.useSkill();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastHate(int value) {
/* 316 */     for (VisibleObject visibleObject : getOwner().getKnownList().getKnownObjects().values()) {
/*     */       
/* 318 */       if (visibleObject instanceof Creature)
/*     */       {
/* 320 */         ((Creature)visibleObject).getAggroList().notifyHate(getOwner(), value);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void abortCast() {
/* 326 */     Creature creature = getOwner();
/* 327 */     Skill skill = creature.getCastingSkill();
/* 328 */     if (skill == null)
/*     */       return; 
/* 330 */     creature.setCasting(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelCurrentSkill() {
/* 338 */     Creature creature = getOwner();
/* 339 */     Skill castingSkill = creature.getCastingSkill();
/* 340 */     if (castingSkill != null) {
/*     */       
/* 342 */       creature.removeSkillCoolDown(castingSkill.getSkillTemplate().getSkillId());
/* 343 */       creature.setCasting(null);
/* 344 */       PacketSendUtility.broadcastPacketAndReceive((VisibleObject)creature, (AionServerPacket)new SM_SKILL_CANCEL(creature, castingSkill.getSkillTemplate().getSkillId()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void createSummon(int npcId, int skillLvl) {}
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\CreatureController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */