/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.templates.item.ArmorType;
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
/*    */ @XmlType(name = "ArmorMasteryEffect")
/*    */ public class ArmorMasteryEffect
/*    */   extends BufEffect
/*    */ {
/*    */   @XmlAttribute(name = "armor")
/*    */   private ArmorType armorType;
/*    */   
/*    */   public ArmorType getArmorType() {
/* 44 */     return this.armorType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 51 */     Player player = (Player)effect.getEffector();
/*    */     
/* 53 */     Integer skillId = player.getSkillList().getArmorMasterySkill(this.armorType);
/* 54 */     if (skillId != null && skillId.intValue() != effect.getSkillId()) {
/*    */       return;
/*    */     }
/* 57 */     boolean armorMasterySet = player.getEffectController().isArmorMasterySet(skillId.intValue());
/* 58 */     boolean isArmorEquiped = player.getEquipment().isArmorEquipped(this.armorType);
/* 59 */     if (!armorMasterySet && isArmorEquiped) {
/* 60 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 66 */     super.startEffect(effect);
/* 67 */     Player player = (Player)effect.getEffector();
/* 68 */     player.getEffectController().removeEffect(effect.getSkillId());
/* 69 */     player.getEffectController().setArmorMastery(effect.getSkillId());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 75 */     super.endEffect(effect);
/* 76 */     Player player = (Player)effect.getEffector();
/* 77 */     player.getEffectController().unsetArmorMastery();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ArmorMasteryEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */