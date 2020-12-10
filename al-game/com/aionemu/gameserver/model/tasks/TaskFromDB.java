/*     */ package com.aionemu.gameserver.model.tasks;
/*     */ 
/*     */ import java.sql.Timestamp;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskFromDB
/*     */ {
/*     */   private int id;
/*     */   private String name;
/*     */   private String type;
/*     */   private Timestamp lastActivation;
/*     */   private String startTime;
/*     */   private int delay;
/*     */   private String[] params;
/*     */   
/*     */   public TaskFromDB(int id, String name, String type, Timestamp lastActivation, String startTime, int delay, String param) {
/*  48 */     this.id = id;
/*  49 */     this.name = name;
/*  50 */     this.type = type;
/*  51 */     this.lastActivation = lastActivation;
/*  52 */     this.startTime = startTime;
/*  53 */     this.delay = delay;
/*     */     
/*  55 */     if (param != null) {
/*  56 */       this.params = param.split(" ");
/*     */     } else {
/*  58 */       this.params = new String[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  68 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  78 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/*  89 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getLastActivation() {
/*  99 */     return this.lastActivation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartTime() {
/* 109 */     return this.startTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDelay() {
/* 119 */     return this.delay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getParams() {
/* 129 */     return this.params;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\tasks\TaskFromDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */