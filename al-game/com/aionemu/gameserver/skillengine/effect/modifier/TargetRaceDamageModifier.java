/*    */ package com.aionemu.gameserver.skillengine.effect.modifier;
/*    */ 
/*    */ import com.aionemu.gameserver.model.Race;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.skillengine.model.SkillTargetRace;
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
/*    */ @XmlType(name = "TargetRaceDamageModifier")
/*    */ public class TargetRaceDamageModifier
/*    */   extends ActionModifier
/*    */ {
/*    */   @XmlAttribute(name = "race")
/*    */   private SkillTargetRace skillTargetRace;
/*    */   @XmlAttribute(required = true)
/*    */   protected int delta;
/*    */   @XmlAttribute(required = true)
/*    */   protected int value;
/*    */   
/*    */   public int analyze(Effect effect, int originalValue) {
/* 48 */     Creature effected = effect.getEffected();
/*    */     
/* 50 */     if (effected instanceof Player) {
/*    */       
/* 52 */       int newValue = originalValue + this.value + effect.getSkillLevel() * this.delta;
/* 53 */       Player player = (Player)effected;
/* 54 */       switch (this.skillTargetRace) {
/*    */         
/*    */         case ASMODIANS:
/* 57 */           if (player.getCommonData().getRace() == Race.ASMODIANS)
/* 58 */             return newValue; 
/*    */           break;
/*    */         case ELYOS:
/* 61 */           if (player.getCommonData().getRace() == Race.ELYOS)
/* 62 */             return newValue; 
/*    */           break;
/*    */       } 
/*    */     } 
/* 66 */     return originalValue;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean check(Effect effect) {
/* 72 */     Creature effected = effect.getEffected();
/* 73 */     if (effected instanceof Player) {
/*    */ 
/*    */       
/* 76 */       Player player = (Player)effected;
/* 77 */       Race race = player.getCommonData().getRace();
/* 78 */       return ((race == Race.ASMODIANS && this.skillTargetRace == SkillTargetRace.ASMODIANS) || (race == Race.ELYOS && this.skillTargetRace == SkillTargetRace.ELYOS));
/*    */     } 
/*    */     
/* 81 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\modifier\TargetRaceDamageModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */