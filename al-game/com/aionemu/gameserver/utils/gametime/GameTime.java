/*     */ package com.aionemu.gameserver.utils.gametime;
/*     */ 
/*     */ import com.aionemu.gameserver.spawnengine.DayNightSpawnManager;
/*     */ import java.security.InvalidParameterException;
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
/*     */ public class GameTime
/*     */ {
/*     */   public static final int MINUTES_IN_YEAR = 535680;
/*     */   public static final int MINUTES_IN_MONTH = 44640;
/*     */   public static final int MINUTES_IN_DAY = 1440;
/*     */   public static final int MINUTES_IN_HOUR = 60;
/*  36 */   private int gameTime = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DayTime dayTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameTime(int time) {
/*  48 */     if (time < 0)
/*  49 */       throw new InvalidParameterException("Time must be >= 0"); 
/*  50 */     this.gameTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTime() {
/*  60 */     return this.gameTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void increase() {
/*  68 */     this.gameTime++;
/*  69 */     if (getMinute() == 0)
/*     */     {
/*  71 */       analyzeDayTime();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void analyzeDayTime() {
/*  80 */     DayTime newDateTime = null;
/*  81 */     int hour = getHour();
/*  82 */     if (hour > 21 || hour < 4) {
/*  83 */       newDateTime = DayTime.NIGHT;
/*  84 */     } else if (hour > 16) {
/*  85 */       newDateTime = DayTime.EVENING;
/*  86 */     } else if (hour > 8) {
/*  87 */       newDateTime = DayTime.AFTERNOON;
/*     */     } else {
/*  89 */       newDateTime = DayTime.MORNING;
/*     */     } 
/*  91 */     if (newDateTime != this.dayTime) {
/*     */       
/*  93 */       this.dayTime = newDateTime;
/*  94 */       DayNightSpawnManager.getInstance().notifyChangeMode();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYear() {
/* 105 */     return this.gameTime / 535680;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonth() {
/* 115 */     return this.gameTime % 535680 / 44640 + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDay() {
/* 125 */     return this.gameTime % 44640 / 1440 + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHour() {
/* 135 */     return this.gameTime % 1440 / 60;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinute() {
/* 145 */     return this.gameTime % 60;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DayTime getDayTime() {
/* 153 */     return this.dayTime;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\gametime\GameTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */