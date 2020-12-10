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
/*    */ @XmlType(name = "SummonEffect")
/*    */ public class SummonEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute(name = "npc_id", required = true)
/*    */   protected int npcId;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 41 */     Creature effected = effect.getEffected();
/* 42 */     effected.getController().createSummon(this.npcId, effect.getSkillLevel());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 48 */     if (effect.getEffected() instanceof com.aionemu.gameserver.model.gameobjects.player.Player)
/* 49 */       effect.addSucessEffect(this); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SummonEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */