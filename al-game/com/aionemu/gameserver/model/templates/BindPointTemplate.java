/*    */ package com.aionemu.gameserver.model.templates;
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
/*    */ @XmlRootElement(name = "bind_points")
/*    */ @XmlAccessorType(XmlAccessType.NONE)
/*    */ public class BindPointTemplate
/*    */ {
/*    */   @XmlAttribute(name = "name", required = true)
/*    */   private String name;
/*    */   @XmlAttribute(name = "npcid")
/*    */   private int npcId;
/*    */   @XmlAttribute(name = "bindid")
/*    */   private int bindId;
/*    */   @XmlAttribute(name = "mapid")
/* 42 */   private int mapId = 0;
/*    */   
/*    */   @XmlAttribute(name = "posX")
/* 45 */   private float x = 0.0F;
/*    */   
/*    */   @XmlAttribute(name = "posY")
/* 48 */   private float y = 0.0F;
/*    */   
/*    */   @XmlAttribute(name = "posZ")
/* 51 */   private float z = 0.0F;
/*    */   
/*    */   @XmlAttribute(name = "price")
/* 54 */   private int price = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 59 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNpcId() {
/* 64 */     return this.npcId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBindId() {
/* 69 */     return this.bindId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getZoneId() {
/* 74 */     return this.mapId;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getX() {
/* 79 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getY() {
/* 84 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getZ() {
/* 89 */     return this.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPrice() {
/* 94 */     return this.price;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\BindPointTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */