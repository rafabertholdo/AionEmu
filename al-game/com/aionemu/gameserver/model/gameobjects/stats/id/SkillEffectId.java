/*    */ package com.aionemu.gameserver.model.gameobjects.stats.id;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;
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
/*    */ public class SkillEffectId
/*    */   extends StatEffectId
/*    */ {
/*    */   private int effectId;
/*    */   private int effectOrder;
/*    */   
/*    */   private SkillEffectId(int skillId, int effectId, int effectOrder) {
/* 32 */     super(skillId, StatEffectType.SKILL_EFFECT);
/* 33 */     this.effectId = effectId;
/* 34 */     this.effectOrder = effectOrder;
/*    */   }
/*    */ 
/*    */   
/*    */   public static SkillEffectId getInstance(int skillId, int effectId, int effectOrder) {
/* 39 */     return new SkillEffectId(skillId, effectId, effectOrder);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 45 */     boolean result = super.equals(o);
/* 46 */     result = (result && o != null);
/* 47 */     result = (result && o instanceof SkillEffectId);
/* 48 */     result = (result && ((SkillEffectId)o).effectId == this.effectId);
/* 49 */     result = (result && ((SkillEffectId)o).effectOrder == this.effectOrder);
/* 50 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(StatEffectId o) {
/* 56 */     int result = super.compareTo(o);
/* 57 */     if (result == 0)
/*    */     {
/* 59 */       if (o instanceof SkillEffectId) {
/*    */         
/* 61 */         result = this.effectId - ((SkillEffectId)o).effectId;
/* 62 */         if (result == 0)
/* 63 */           result = this.effectOrder - ((SkillEffectId)o).effectOrder; 
/*    */       } 
/*    */     }
/* 66 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     String str = super.toString() + ",effectId:" + this.effectId + ",effectOrder:" + this.effectOrder;
/* 73 */     return str;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEffectId() {
/* 81 */     return this.effectId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEffectOrder() {
/* 89 */     return this.effectOrder;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\SkillEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */