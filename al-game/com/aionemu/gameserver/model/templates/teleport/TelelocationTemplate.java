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
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "teleloc_template")
/*    */ @XmlAccessorType(XmlAccessType.NONE)
/*    */ public class TelelocationTemplate
/*    */ {
/*    */   @XmlAttribute(name = "loc_id", required = true)
/*    */   private int locId;
/*    */   @XmlAttribute(name = "mapid", required = true)
/* 38 */   private int mapid = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   @XmlAttribute(name = "name", required = true)
/* 43 */   private String name = "";
/*    */ 
/*    */   
/*    */   @XmlAttribute(name = "posX")
/* 47 */   private float x = 0.0F;
/*    */   
/*    */   @XmlAttribute(name = "posY")
/* 50 */   private float y = 0.0F;
/*    */   
/*    */   @XmlAttribute(name = "posZ")
/* 53 */   private float z = 0.0F;
/*    */   
/*    */   @XmlAttribute(name = "heading")
/* 56 */   private int heading = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLocId() {
/* 61 */     return this.locId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMapId() {
/* 66 */     return this.mapid;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 71 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getX() {
/* 77 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getY() {
/* 82 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getZ() {
/* 87 */     return this.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeading() {
/* 92 */     return this.heading;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\teleport\TelelocationTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */