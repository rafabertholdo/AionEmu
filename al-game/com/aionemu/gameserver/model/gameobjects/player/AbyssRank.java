/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
/*     */ import java.util.Calendar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbyssRank
/*     */ {
/*     */   private int dailyAP;
/*     */   private int weeklyAP;
/*     */   private int ap;
/*     */   private AbyssRankEnum rank;
/*     */   private int topRanking;
/*     */   private PersistentState persistentState;
/*     */   private int dailyKill;
/*     */   private int weeklyKill;
/*     */   private int allKill;
/*     */   private int maxRank;
/*     */   private int lastKill;
/*     */   private int lastAP;
/*     */   private long lastUpdate;
/*     */   
/*     */   public AbyssRank(int dailyAP, int weeklyAP, int ap, int rank, int topRanking, int dailyKill, int weeklyKill, int allKill, int maxRank, int lastKill, int lastAP, long lastUpdate) {
/*  63 */     this.dailyAP = dailyAP;
/*  64 */     this.weeklyAP = weeklyAP;
/*  65 */     this.ap = ap;
/*  66 */     this.rank = AbyssRankEnum.getRankById(rank);
/*  67 */     this.topRanking = topRanking;
/*  68 */     this.dailyKill = dailyKill;
/*  69 */     this.weeklyKill = weeklyKill;
/*  70 */     this.allKill = allKill;
/*  71 */     this.maxRank = maxRank;
/*  72 */     this.lastKill = lastKill;
/*  73 */     this.lastAP = lastAP;
/*  74 */     this.lastUpdate = lastUpdate;
/*     */     
/*  76 */     doUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAp(int ap) {
/*  86 */     this.dailyAP += ap;
/*  87 */     if (this.dailyAP < 0) {
/*  88 */       this.dailyAP = 0;
/*     */     }
/*  90 */     this.weeklyAP += ap;
/*  91 */     if (this.weeklyAP < 0) {
/*  92 */       this.weeklyAP = 0;
/*     */     }
/*  94 */     setAp(this.ap + ap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDailyAP() {
/* 102 */     return this.dailyAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeeklyAP() {
/* 110 */     return this.weeklyAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAp() {
/* 118 */     return this.ap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAp(int ap) {
/* 128 */     if (ap < 0)
/* 129 */       ap = 0; 
/* 130 */     this.ap = ap;
/*     */     
/* 132 */     AbyssRankEnum newRank = AbyssRankEnum.getRankForAp(this.ap);
/* 133 */     if (newRank != this.rank) {
/* 134 */       setRank(newRank);
/*     */     }
/* 136 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbyssRankEnum getRank() {
/* 144 */     return this.rank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTopRanking() {
/* 152 */     return this.topRanking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTopRanking(int topRanking) {
/* 160 */     this.topRanking = topRanking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDailyKill() {
/* 168 */     return this.dailyKill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeeklyKill() {
/* 176 */     return this.weeklyKill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAllKill() {
/* 184 */     return this.allKill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllKill() {
/* 192 */     this.dailyKill++;
/* 193 */     this.weeklyKill++;
/* 194 */     this.allKill++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRank() {
/* 202 */     return this.maxRank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLastKill() {
/* 210 */     return this.lastKill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLastAP() {
/* 218 */     return this.lastAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRank(AbyssRankEnum rank) {
/* 226 */     if (rank.getId() > this.maxRank) {
/* 227 */       this.maxRank = rank.getId();
/*     */     }
/* 229 */     this.rank = rank;
/*     */ 
/*     */     
/* 232 */     setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PersistentState getPersistentState() {
/* 240 */     return this.persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentState(PersistentState persistentState) {
/* 248 */     switch (persistentState) {
/*     */       
/*     */       case UPDATE_REQUIRED:
/* 251 */         if (this.persistentState == PersistentState.NEW)
/*     */           return;  break;
/*     */     } 
/* 254 */     this.persistentState = persistentState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastUpdate() {
/* 263 */     return this.lastUpdate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doUpdate() {
/* 271 */     boolean needUpdate = false;
/* 272 */     Calendar lastCal = Calendar.getInstance();
/* 273 */     lastCal.setTimeInMillis(this.lastUpdate);
/*     */     
/* 275 */     Calendar curCal = Calendar.getInstance();
/* 276 */     curCal.setTimeInMillis(System.currentTimeMillis());
/*     */ 
/*     */     
/* 279 */     if (lastCal.get(5) != curCal.get(5) || lastCal.get(2) != curCal.get(2) || lastCal.get(1) != curCal.get(1)) {
/*     */ 
/*     */ 
/*     */       
/* 283 */       this.dailyAP = 0;
/* 284 */       this.dailyKill = 0;
/* 285 */       needUpdate = true;
/*     */     } 
/*     */ 
/*     */     
/* 289 */     if (lastCal.get(3) != curCal.get(3) || lastCal.get(1) != curCal.get(1)) {
/*     */ 
/*     */       
/* 292 */       this.lastKill = this.weeklyKill;
/* 293 */       this.lastAP = this.weeklyAP;
/* 294 */       this.weeklyKill = 0;
/* 295 */       this.weeklyAP = 0;
/* 296 */       needUpdate = true;
/*     */     } 
/*     */ 
/*     */     
/* 300 */     this.lastUpdate = System.currentTimeMillis();
/*     */     
/* 302 */     if (needUpdate)
/* 303 */       setPersistentState(PersistentState.UPDATE_REQUIRED); 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\AbyssRank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */