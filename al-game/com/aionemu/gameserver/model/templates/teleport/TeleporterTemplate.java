/*    */ package com.aionemu.gameserver.model.templates.teleport;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
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
/*    */ @XmlRootElement(name = "teleporter_template")
/*    */ @XmlAccessorType(XmlAccessType.NONE)
/*    */ public class TeleporterTemplate
/*    */ {
/*    */   @XmlAttribute(name = "npc_id", required = true)
/*    */   private int npcId;
/*    */   @XmlAttribute(name = "name", required = true)
/* 36 */   private String name = "";
/*    */   
/*    */   @XmlAttribute(name = "teleportId", required = true)
/* 39 */   private int teleportId = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   @XmlAttribute(name = "type", required = true)
/*    */   private TeleportType type;
/*    */ 
/*    */   
/*    */   @XmlElement(name = "locations")
/*    */   private TeleLocIdData teleLocIdData;
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNpcId() {
/* 53 */     return this.npcId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 61 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTeleportId() {
/* 69 */     return this.teleportId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportType getType() {
/* 77 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleLocIdData getTeleLocIdData() {
/* 85 */     return this.teleLocIdData;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\teleport\TeleporterTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */