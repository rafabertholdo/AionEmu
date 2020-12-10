/*     */ package com.aionemu.gameserver.world.zone;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.zone.Point2D;
/*     */ import com.aionemu.gameserver.model.templates.zone.ZoneTemplate;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZoneInstance
/*     */ {
/*     */   private int corners;
/*     */   private float[] xCoordinates;
/*     */   private float[] yCoordinates;
/*     */   private ZoneTemplate template;
/*     */   private Collection<ZoneInstance> neighbors;
/*     */   
/*     */   public ZoneInstance(ZoneTemplate template) {
/*  40 */     this.template = template;
/*  41 */     this.corners = template.getPoints().getPoint().size();
/*  42 */     this.xCoordinates = new float[this.corners];
/*  43 */     this.yCoordinates = new float[this.corners];
/*  44 */     for (int i = 0; i < this.corners; i++) {
/*     */       
/*  46 */       Point2D point = template.getPoints().getPoint().get(i);
/*  47 */       this.xCoordinates[i] = point.getX();
/*  48 */       this.yCoordinates[i] = point.getY();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCorners() {
/*  57 */     return this.corners;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getxCoordinates() {
/*  65 */     return this.xCoordinates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getyCoordinates() {
/*  73 */     return this.yCoordinates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ZoneInstance> getNeighbors() {
/*  81 */     return this.neighbors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNeighbors(Collection<ZoneInstance> neighbours) {
/*  90 */     this.neighbors = neighbours;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneTemplate getTemplate() {
/*  98 */     return this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTop() {
/* 108 */     return this.template.getPoints().getTop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBottom() {
/* 118 */     return this.template.getPoints().getBottom();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreath() {
/* 128 */     return this.template.isBreath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPriority() {
/* 138 */     return this.template.getPriority();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\zone\ZoneInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */