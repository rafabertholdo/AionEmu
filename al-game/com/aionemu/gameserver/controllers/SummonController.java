/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.attack.AttackResult;
/*     */ import com.aionemu.gameserver.controllers.attack.AttackUtil;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.TaskId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_OWNER_REMOVE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_PANEL_REMOVE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.services.LifeStatsRestoreService;
/*     */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.List;
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
/*     */ public class SummonController
/*     */   extends CreatureController<Summon>
/*     */ {
/*  52 */   private long lastAttackMilis = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   public void notSee(VisibleObject object, boolean isOutOfRange) {
/*  57 */     super.notSee(object, isOutOfRange);
/*  58 */     if (getOwner().getMaster() == null) {
/*     */       return;
/*     */     }
/*  61 */     if (object.getObjectId() == getOwner().getMaster().getObjectId())
/*     */     {
/*  63 */       release(UnsummonType.DISTANCE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Summon getOwner() {
/*  70 */     return (Summon)super.getOwner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release(final UnsummonType unsummonType) {
/*  78 */     final Summon owner = getOwner();
/*     */     
/*  80 */     if (owner.getMode() == Summon.SummonMode.RELEASE)
/*     */       return; 
/*  82 */     owner.setMode(Summon.SummonMode.RELEASE);
/*     */     
/*  84 */     final Player master = owner.getMaster();
/*  85 */     final int summonObjId = owner.getObjectId();
/*     */     
/*  87 */     switch (unsummonType) {
/*     */       
/*     */       case COMMAND:
/*  90 */         PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_UNSUMMON(getOwner().getNameId()));
/*  91 */         PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
/*     */         break;
/*     */       case DISTANCE:
/*  94 */         PacketSendUtility.sendPacket(getOwner().getMaster(), (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_UNSUMMON_BY_TOO_DISTANCE());
/*     */         
/*  96 */         PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 108 */             owner.setMaster(null);
/* 109 */             master.setSummon(null);
/* 110 */             owner.getController().delete();
/*     */             
/* 112 */             switch (unsummonType) {
/*     */               
/*     */               case COMMAND:
/*     */               case DISTANCE:
/*     */               case UNSPECIFIED:
/* 117 */                 PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_DISMISSED(SummonController.this.getOwner().getNameId()));
/*     */                 
/* 119 */                 PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_OWNER_REMOVE(summonObjId));
/*     */ 
/*     */                 
/* 122 */                 PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_PANEL_REMOVE());
/*     */                 break;
/*     */             } 
/*     */           }
/*     */         }5000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restMode() {
/* 136 */     getOwner().setMode(Summon.SummonMode.REST);
/* 137 */     Player master = getOwner().getMaster();
/* 138 */     PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_RESTMODE(getOwner().getNameId()));
/* 139 */     PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
/* 140 */     checkCurrentHp();
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkCurrentHp() {
/* 145 */     if (!getOwner().getLifeStats().isFullyRestoredHp()) {
/* 146 */       getOwner().getController().addNewTask(TaskId.RESTORE, LifeStatsRestoreService.getInstance().scheduleHpRestoreTask(getOwner().getLifeStats()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void guardMode() {
/* 155 */     getOwner().setMode(Summon.SummonMode.GUARD);
/* 156 */     Player master = getOwner().getMaster();
/* 157 */     PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_GUARDMODE(getOwner().getNameId()));
/* 158 */     PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
/* 159 */     checkCurrentHp();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackMode() {
/* 167 */     getOwner().setMode(Summon.SummonMode.ATTACK);
/* 168 */     Player master = getOwner().getMaster();
/* 169 */     PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_ATTACKMODE(getOwner().getNameId()));
/* 170 */     PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
/* 171 */     getOwner().getController().cancelTask(TaskId.RESTORE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackTarget(Creature target) {
/* 177 */     Summon summon = getOwner();
/* 178 */     Player master = getOwner().getMaster();
/* 179 */     if (!summon.canAttack()) {
/*     */       return;
/*     */     }
/* 182 */     if (!RestrictionsManager.canAttack(master, (VisibleObject)target)) {
/*     */       return;
/*     */     }
/* 185 */     if (!summon.isEnemy((VisibleObject)target)) {
/*     */       return;
/*     */     }
/* 188 */     int attackSpeed = summon.getGameStats().getCurrentStat(StatEnum.ATTACK_SPEED);
/* 189 */     long milis = System.currentTimeMillis();
/* 190 */     if (milis - this.lastAttackMilis < attackSpeed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     this.lastAttackMilis = milis;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     super.attackTarget(target);
/*     */     
/* 204 */     List<AttackResult> attackList = AttackUtil.calculateAttackResult((Creature)summon, target);
/*     */     
/* 206 */     int damage = 0;
/* 207 */     for (AttackResult result : attackList)
/*     */     {
/* 209 */       damage += result.getDamage();
/*     */     }
/*     */     
/* 212 */     int attackType = 0;
/* 213 */     PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_ATTACK((Creature)summon, target, summon.getGameStats().getAttackCounter(), 274, attackType, attackList));
/*     */ 
/*     */     
/* 216 */     target.getController().onAttack((Creature)summon, damage);
/* 217 */     summon.getGameStats().increaseAttackCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
/* 224 */     if (getOwner().getLifeStats().isAlreadyDead()) {
/*     */       return;
/*     */     }
/*     */     
/* 228 */     if (getOwner().getMode() == Summon.SummonMode.RELEASE) {
/*     */       return;
/*     */     }
/* 231 */     super.onAttack(creature, skillId, type, damage);
/* 232 */     getOwner().getLifeStats().reduceHp(damage, creature);
/* 233 */     PacketSendUtility.broadcastPacket((VisibleObject)getOwner(), (AionServerPacket)new SM_ATTACK_STATUS((Creature)getOwner(), SM_ATTACK_STATUS.TYPE.REGULAR, 0, damage));
/*     */     
/* 235 */     PacketSendUtility.sendPacket(getOwner().getMaster(), (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDie(Creature lastAttacker) {
/* 241 */     super.onDie(lastAttacker);
/* 242 */     release(UnsummonType.UNSPECIFIED);
/* 243 */     Summon owner = getOwner();
/* 244 */     PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_EMOTION((Creature)owner, EmotionType.DIE, 0, (lastAttacker == null) ? 0 : lastAttacker.getObjectId()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void useSkill(int skillId, Creature target) {
/* 250 */     Summon summon = getOwner();
/*     */     
/* 252 */     Skill skill = SkillEngine.getInstance().getSkill((Creature)summon, skillId, 1, (VisibleObject)target);
/* 253 */     if (skill != null)
/*     */     {
/* 255 */       skill.useSkill();
/*     */     }
/*     */   }
/*     */   
/*     */   public enum UnsummonType
/*     */   {
/* 261 */     LOGOUT,
/* 262 */     DISTANCE,
/* 263 */     COMMAND,
/* 264 */     UNSPECIFIED;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\SummonController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */