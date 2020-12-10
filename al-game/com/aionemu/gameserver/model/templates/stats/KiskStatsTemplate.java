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
/*    */ @XmlRootElement(name = "kisk_stats")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class KiskStatsTemplate
/*    */ {
/*    */   @XmlAttribute(name = "usemask")
/* 33 */   private int useMask = 4;
/*    */   
/*    */   @XmlAttribute(name = "members")
/* 36 */   private int maxMembers = 6;
/*    */   
/*    */   @XmlAttribute(name = "resurrects")
/* 39 */   private int maxResurrects = 18;
/*    */ 
/*    */ 
/*    */   
/*    */   public int getUseMask() {
/* 44 */     return this.useMask;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxMembers() {
/* 49 */     return this.maxMembers;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxResurrects() {
/* 54 */     return this.maxResurrects;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\stats\KiskStatsTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */