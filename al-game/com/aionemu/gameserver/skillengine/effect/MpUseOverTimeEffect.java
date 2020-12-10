/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "MpUseOverTimeEffect")
/*    */ public class MpUseOverTimeEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute(required = true)
/*    */   protected int checktime;
/*    */   @XmlAttribute
/*    */   protected int value;
/*    */   
/*    */   public void applyEffect(final Effect effect) {
/* 47 */     Creature effected = effect.getEffected();
/* 48 */     int maxMp = effected.getGameStats().getCurrentStat(StatEnum.MAXMP);
/* 49 */     final int requiredMp = maxMp * this.value / 100;
/*    */     
/* 51 */     Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 56 */             MpUseOverTimeEffect.this.onPeriodicAction(effect, requiredMp);
/*    */           }
/*    */         }0L, this.checktime);
/* 59 */     effect.setMpUseTask(task);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPeriodicAction(Effect effect, int value) {
/* 64 */     Creature effected = effect.getEffected();
/* 65 */     if (effected.getLifeStats().getCurrentMp() < value) {
/* 66 */       effect.endEffect();
/*    */     }
/* 68 */     effected.getLifeStats().reduceMp(value);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 74 */     Creature effected = effect.getEffected();
/* 75 */     int maxMp = effected.getGameStats().getCurrentStat(StatEnum.MAXMP);
/* 76 */     int requiredMp = maxMp * this.value / 100;
/* 77 */     if (effected.getLifeStats().getCurrentMp() < requiredMp) {
/*    */       return;
/*    */     }
/* 80 */     effect.addSucessEffect(this);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\MpUseOverTimeEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */