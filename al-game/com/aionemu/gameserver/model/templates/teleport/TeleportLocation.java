/*    */ package com.aionemu.gameserver.model.templates.teleport;
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
/*    */ @XmlRootElement(name = "telelocation")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class TeleportLocation
/*    */ {
/*    */   @XmlAttribute(name = "loc_id", required = true)
/*    */   private int locId;
/*    */   @XmlAttribute(name = "teleportid")
/* 35 */   private int teleportid = 0;
/*    */   
/*    */   @XmlAttribute(name = "price", required = true)
/* 38 */   private int price = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLocId() {
/* 43 */     return this.locId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTeleportId() {
/* 48 */     return this.teleportid;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPrice() {
/* 53 */     return this.price;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\teleport\TeleportLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */