/*     */ package com.aionemu.gameserver.restrictions;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
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
/*     */ public abstract class AbstractRestrictions
/*     */   implements Restrictions
/*     */ {
/*     */   public void activate() {
/*  31 */     RestrictionsManager.activate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/*  36 */     RestrictionsManager.deactivate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  42 */     return getClass().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  51 */     return getClass().equals(obj.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
/*  57 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canAttack(Player player, VisibleObject target) {
/*  63 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canAffectBySkill(Player player, VisibleObject target) {
/*  69 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canUseSkill(Player player, Skill skill) {
/*  75 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canChat(Player player) {
/*  81 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canInviteToGroup(Player player, Player target) {
/*  87 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canChangeEquip(Player player) {
/*  93 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canUseWarehouse(Player player) {
/*  99 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canTrade(Player player) {
/* 105 */     throw new AbstractMethodError();
/*     */   }
/*     */ 
/*     */   
/*     */   @DisabledRestriction
/*     */   public boolean canUseItem(Player player) {
/* 111 */     throw new AbstractMethodError();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\restrictions\AbstractRestrictions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */