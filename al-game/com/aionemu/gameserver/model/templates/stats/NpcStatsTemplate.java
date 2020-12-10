/*    */ package com.aionemu.gameserver.model.templates.stats;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
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
/*    */ @XmlRootElement(name = "npc_stats_template")
/*    */ public class NpcStatsTemplate
/*    */   extends StatsTemplate
/*    */ {
/*    */   @XmlAttribute(name = "run_speed_fight")
/*    */   private float runSpeedFight;
/*    */   @XmlAttribute(name = "pdef")
/*    */   private int pdef;
/*    */   @XmlAttribute(name = "mdef")
/*    */   private int mdef;
/*    */   @XmlAttribute(name = "crit")
/*    */   private int crit;
/*    */   @XmlAttribute(name = "accuracy")
/*    */   private int accuracy;
/*    */   @XmlAttribute(name = "power")
/*    */   private int power;
/*    */   @XmlAttribute(name = "maxXp")
/*    */   private int maxXp;
/*    */   
/*    */   public float getRunSpeedFight() {
/* 49 */     return this.runSpeedFight;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPdef() {
/* 57 */     return this.pdef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getMdef() {
/* 65 */     return this.mdef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getCrit() {
/* 73 */     return this.crit;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getAccuracy() {
/* 81 */     return this.accuracy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPower() {
/* 89 */     return this.power;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxXp() {
/* 97 */     return this.maxXp;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\stats\NpcStatsTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */