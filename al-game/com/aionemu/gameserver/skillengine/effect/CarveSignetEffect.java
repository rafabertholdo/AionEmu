/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.skillengine.action.DamageType;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "CarveSignetEffect")
/*    */ public class CarveSignetEffect
/*    */   extends DamageEffect
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int signetlvl;
/*    */   @XmlAttribute(required = true)
/*    */   protected int signetid;
/*    */   @XmlAttribute(required = true)
/*    */   protected String signet;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 48 */     super.applyEffect(effect);
/*    */     
/* 50 */     Creature effected = effect.getEffected();
/* 51 */     Effect placedSignet = effected.getEffectController().getAnormalEffect(this.signet);
/* 52 */     int nextSignetlvl = 1;
/* 53 */     if (placedSignet != null) {
/*    */       
/* 55 */       nextSignetlvl = placedSignet.getSkillId() - this.signetid + 2;
/* 56 */       if (nextSignetlvl > this.signetlvl || nextSignetlvl > 5)
/*    */         return; 
/* 58 */       placedSignet.endEffect();
/*    */     } 
/*    */     
/* 61 */     SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.signetid + nextSignetlvl - 1);
/* 62 */     int effectsDuration = template.getEffectsDuration();
/* 63 */     Effect newEffect = new Effect(effect.getEffector(), effect.getEffected(), template, nextSignetlvl, effectsDuration);
/* 64 */     newEffect.initialize();
/* 65 */     newEffect.applyEffect();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 71 */     calculate(effect, DamageType.PHYSICAL);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\CarveSignetEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */