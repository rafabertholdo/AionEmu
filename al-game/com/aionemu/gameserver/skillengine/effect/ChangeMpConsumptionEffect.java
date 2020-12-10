/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.skillengine.model.Skill;
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
/*    */ @XmlType(name = "ChangeMpConsumptionEffect")
/*    */ public class ChangeMpConsumptionEffect
/*    */   extends BufEffect
/*    */ {
/*    */   @XmlAttribute
/*    */   protected boolean percent;
/*    */   @XmlAttribute
/*    */   protected int value;
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 44 */     super.startEffect(effect);
/*    */     
/* 46 */     ActionObserver observer = new ActionObserver(ActionObserver.ObserverType.SKILLUSE)
/*    */       {
/*    */         
/*    */         public void skilluse(Skill skill)
/*    */         {
/* 51 */           skill.setChangeMpConsumption(ChangeMpConsumptionEffect.this.value);
/*    */         }
/*    */       };
/*    */     
/* 55 */     effect.getEffected().getObserveController().addObserver(observer);
/* 56 */     effect.setActionObserver(observer, this.position);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 62 */     super.endEffect(effect);
/* 63 */     ActionObserver observer = effect.getActionObserver(this.position);
/* 64 */     effect.getEffected().getObserveController().removeObserver(observer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 70 */     super.calculate(effect);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ChangeMpConsumptionEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */