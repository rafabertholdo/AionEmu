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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "WeaponStatboostEffect")
/*     */ public class WeaponStatboostEffect
/*     */   extends BufEffect
/*     */ {
/*     */   @XmlAttribute(name = "weapon")
/*     */   private WeaponType weaponType;
/*     */   
/*     */   public void startEffect(Effect effect) {
/*  49 */     final Player effected = (Player)effect.getEffected();
/*     */     
/*  51 */     final SkillEffectId skillEffectId = getSkillEffectId(effect);
/*  52 */     final TreeSet<StatModifier> stats = getModifiers(effect);
/*     */     
/*  54 */     if (effected.getEquipment().isWeaponEquipped(this.weaponType)) {
/*  55 */       effected.getGameStats().addModifiers((StatEffectId)skillEffectId, stats);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     ActionObserver aObserver = new ActionObserver(ActionObserver.ObserverType.EQUIP)
/*     */       {
/*     */         
/*     */         public void equip(Item item, Player owner)
/*     */         {
/*  66 */           if (item.getItemTemplate().getWeaponType() == WeaponStatboostEffect.this.weaponType) {
/*  67 */             effected.getGameStats().addModifiers((StatEffectId)skillEffectId, stats);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void unequip(Item item, Player owner) {
/*  73 */           if (item.getItemTemplate().getWeaponType() == WeaponStatboostEffect.this.weaponType) {
/*  74 */             effected.getGameStats().endEffect((StatEffectId)skillEffectId);
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*  79 */     effected.getObserveController().addEquipObserver(aObserver);
/*  80 */     effect.setActionObserver(aObserver, this.position);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEffect(Effect effect) {
/*  86 */     ActionObserver observer = effect.getActionObserver(this.position);
/*  87 */     if (observer != null) {
/*  88 */       effect.getEffected().getObserveController().removeEquipObserver(observer);
/*     */     }
/*  90 */     SkillEffectId skillEffectId = getSkillEffectId(effect);
/*     */     
/*  92 */     if (effect.getEffected().getGameStats().effectAlreadyAdded((StatEffectId)skillEffectId)) {
/*  93 */       effect.getEffected().getGameStats().endEffect((StatEffectId)skillEffectId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEffect(Effect effect) {
/* 100 */     effect.addToEffectedController();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculate(Effect effect) {
/* 106 */     effect.addSucessEffect(this);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\WeaponStatboostEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */