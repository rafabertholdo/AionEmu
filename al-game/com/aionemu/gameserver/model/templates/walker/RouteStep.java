/*    */ package com.aionemu.gameserver.model.templates.walker;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "routestep")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class RouteStep
/*    */ {
/*    */   @XmlAttribute(name = "step", required = true)
/*    */   private int step;
/*    */   @XmlAttribute(name = "loc_x", required = true)
/*    */   private float locX;
/*    */   @XmlAttribute(name = "loc_y", required = true)
/*    */   private float locY;
/*    */   @XmlAttribute(name = "loc_z", required = true)
/*    */   private float locZ;
/*    */   @XmlAttribute(name = "rest_time", required = true)
/*    */   private int time;
/*    */   
/*    */   public RouteStep() {}
/*    */   
/*    */   public RouteStep(float x, float y, float z) {
/* 53 */     this.locX = x;
/* 54 */     this.locY = y;
/* 55 */     this.locZ = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getX() {
/* 60 */     return this.locX;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getY() {
/* 65 */     return this.locY;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getZ() {
/* 70 */     return this.locZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRouteStep() {
/* 75 */     return this.step;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRestTime() {
/* 80 */     return this.time;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\walker\RouteStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */