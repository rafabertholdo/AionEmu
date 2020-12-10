/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "SignetBurstEffect")
/*    */ public class SignetBurstEffect
/*    */   extends DamageEffect
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int signetlvl;
/*    */   @XmlAttribute
/*    */   protected String signet;
/*    */   
/*    */   public void calculate(Effect effect) {
/* 43 */     Creature effected = effect.getEffected();
/* 44 */     Effect signetEffect = effected.getEffectController().getAnormalEffect(this.signet);
/* 45 */     if (signetEffect == null) {
/*    */       return;
/*    */     }
/* 48 */     int level = signetEffect.getSkillLevel();
/* 49 */     int valueWithDelta = this.value + this.delta * effect.getSkillLevel();
/* 50 */     int finalDamage = valueWithDelta * level / 5;
/*    */     
/* 52 */     effect.setReserved1(finalDamage);
/* 53 */     effect.addSucessEffect(this);
/*    */     
/* 55 */     signetEffect.endEffect();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SignetBurstEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */