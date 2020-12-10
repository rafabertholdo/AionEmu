/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.templates.item.WeaponType;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "WeaponMasteryEffect")
/*    */ public class WeaponMasteryEffect
/*    */   extends BufEffect
/*    */ {
/*    */   @XmlAttribute(name = "weapon")
/*    */   private WeaponType weaponType;
/*    */   
/*    */   public WeaponType getWeaponType() {
/* 44 */     return this.weaponType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 51 */     Player player = (Player)effect.getEffector();
/*    */     
/* 53 */     Integer skillId = player.getSkillList().getWeaponMasterySkill(this.weaponType);
/* 54 */     if (skillId != null && skillId.intValue() != effect.getSkillId()) {
/*    */       return;
/*    */     }
/* 57 */     boolean weaponMasterySet = player.getEffectController().isWeaponMasterySet(skillId.intValue());
/* 58 */     boolean isWeaponEquiped = player.getEquipment().isWeaponEquipped(this.weaponType);
/* 59 */     if (!weaponMasterySet && isWeaponEquiped) {
/* 60 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 66 */     super.endEffect(effect);
/* 67 */     Player player = (Player)effect.getEffector();
/* 68 */     player.getEffectController().unsetWeaponMastery();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 74 */     Player player = (Player)effect.getEffector();
/* 75 */     player.getEffectController().removeEffect(player.getEffectController().getWeaponMastery());
/* 76 */     super.startEffect(effect);
/* 77 */     player.getEffectController().setWeaponMastery(effect.getSkillId());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\WeaponMasteryEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */