/*    */ package com.aionemu.gameserver.model.templates.item;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.NONE)
/*    */ @XmlRootElement(name = "Godstone")
/*    */ public class GodstoneInfo
/*    */ {
/*    */   @XmlAttribute
/*    */   private int skillid;
/*    */   @XmlAttribute
/*    */   private int skilllvl;
/*    */   @XmlAttribute
/*    */   private int probability;
/*    */   @XmlAttribute
/*    */   private int probabilityleft;
/*    */   
/*    */   public int getSkillid() {
/* 45 */     return this.skillid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSkilllvl() {
/* 52 */     return this.skilllvl;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getProbability() {
/* 59 */     return this.probability;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getProbabilityleft() {
/* 66 */     return this.probabilityleft;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\GodstoneInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */