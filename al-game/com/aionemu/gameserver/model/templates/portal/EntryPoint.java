/*    */ package com.aionemu.gameserver.model.templates.portal;
/*    */ 
/*    */ import com.aionemu.gameserver.model.Race;
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
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "EntryPoint")
/*    */ public class EntryPoint
/*    */ {
/*    */   @XmlAttribute(name = "mapid")
/*    */   protected int mapId;
/*    */   @XmlAttribute(name = "x")
/*    */   protected float x;
/*    */   @XmlAttribute(name = "y")
/*    */   protected float y;
/*    */   @XmlAttribute(name = "z")
/*    */   protected float z;
/*    */   @XmlAttribute(name = "race")
/*    */   protected Race race;
/*    */   
/*    */   public int getMapId() {
/* 49 */     return this.mapId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getX() {
/* 56 */     return this.x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getY() {
/* 63 */     return this.y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getZ() {
/* 70 */     return this.z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Race getRace() {
/* 77 */     return this.race;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\portal\EntryPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */