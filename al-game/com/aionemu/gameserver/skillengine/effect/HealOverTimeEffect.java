/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.skillengine.model.HealType;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import java.util.concurrent.Future;
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
/*    */ @XmlType(name = "HealOverTimeEffect")
/*    */ public class HealOverTimeEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int checktime;
/*    */   @XmlAttribute
/*    */   protected int value;
/*    */   @XmlAttribute
/*    */   protected int delta;
/*    */   @XmlAttribute
/*    */   protected HealType type;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 52 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 58 */     effect.addSucessEffect(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPeriodicAction(Effect effect) {
/* 70 */     Creature effected = effect.getEffected();
/* 71 */     int valueWithDelta = this.value + this.delta * effect.getSkillLevel();
/* 72 */     effected.getController().onRestore(this.type, valueWithDelta);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(final Effect effect) {
/* 78 */     Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 83 */             HealOverTimeEffect.this.onPeriodicAction(effect);
/*    */           }
/*    */         },  this.checktime, this.checktime);
/* 86 */     effect.setPeriodicTask(task, this.position);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\HealOverTimeEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */