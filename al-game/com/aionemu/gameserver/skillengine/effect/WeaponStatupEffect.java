/*     */ package com.aionemu.gameserver.skillengine.effect;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.SkillEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.templates.item.WeaponType;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import java.util.TreeSet;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "WeaponStatupEffect")
/*     */ public class WeaponStatupEffect
/*     */   extends BufEffect
/*     */ {
/*     */   @XmlAttribute(name = "weapon")
/*     */   private WeaponType weaponType;
/*     */   
/*     */   public void startEffect(Effect effect) {
/*  50 */     if (!(effect.getEffector() instanceof Player)) {
/*     */       return;
/*     */     }
/*     */     
/*  54 */     final Player effected = (Player)effect.getEffected();
/*     */     
/*  56 */     final SkillEffectId skillEffectId = getSkillEffectId(effect);
/*  57 */     final TreeSet<StatModifier> stats = getModifiers(effect);
/*     */     
/*  59 */     if (effected.getEquipment().isWeaponEquipped(this.weaponType)) {
/*  60 */       effected.getGameStats().addModifiers((StatEffectId)skillEffectId, stats);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     ActionObserver aObserver = new ActionObserver(ActionObserver.ObserverType.EQUIP)
/*     */       {
/*     */         
/*     */         public void equip(Item item, Player owner)
/*     */         {
/*  71 */           if (item.getItemTemplate().getWeaponType() == WeaponStatupEffect.this.weaponType) {
/*  72 */             effected.getGameStats().addModifiers((StatEffectId)skillEffectId, stats);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void unequip(Item item, Player owner) {
/*  78 */           if (item.getItemTemplate().getWeaponType() == WeaponStatupEffect.this.weaponType) {
/*  79 */             effected.getGameStats().endEffect((StatEffectId)skillEffectId);
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*  84 */     effected.getObserveController().addEquipObserver(aObserver);
/*  85 */     effect.setActionObserver(aObserver, this.position);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEffect(Effect effect) {
/*  91 */     ActionObserver observer = effect.getActionObserver(this.position);
/*  92 */     if (observer != null) {
/*  93 */       effect.getEffected().getObserveController().removeEquipObserver(observer);
/*     */     }
/*  95 */     SkillEffectId skillEffectId = getSkillEffectId(effect);
/*     */     
/*  97 */     if (effect.getEffected().getGameStats().effectAlreadyAdded((StatEffectId)skillEffectId)) {
/*  98 */       effect.getEffected().getGameStats().endEffect((StatEffectId)skillEffectId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEffect(Effect effect) {
/* 105 */     effect.addToEffectedController();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculate(Effect effect) {
/* 111 */     effect.addSucessEffect(this);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\WeaponStatupEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */