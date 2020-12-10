/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.skillengine.model.DispelType;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
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
/*    */ public class DispelEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlElement(type = Integer.class)
/*    */   protected List<Integer> effectids;
/*    */   @XmlAttribute
/*    */   protected DispelType dispeltype;
/*    */   @XmlAttribute
/*    */   protected Integer value;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 43 */     if (this.effectids == null) {
/*    */       return;
/*    */     }
/* 46 */     switch (this.dispeltype) {
/*    */       
/*    */       case EFFECTID:
/* 49 */         for (Integer effectId : this.effectids)
/*    */         {
/* 51 */           effect.getEffected().getEffectController().removeEffectByEffectId(effectId.intValue());
/*    */         }
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 63 */     effect.addSucessEffect(this);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\DispelEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */