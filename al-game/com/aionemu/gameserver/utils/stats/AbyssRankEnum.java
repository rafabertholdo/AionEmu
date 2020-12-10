/*     */ package com.aionemu.gameserver.utils.stats;
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
/*     */ public enum AbyssRankEnum
/*     */ {
/*  24 */   GRADE9_SOLDIER(1, 120, 24, 0),
/*  25 */   GRADE8_SOLDIER(2, 168, 37, 1200),
/*  26 */   GRADE7_SOLDIER(3, 235, 58, 4220),
/*  27 */   GRADE6_SOLDIER(4, 329, 91, 10990),
/*  28 */   GRADE5_SOLDIER(5, 461, 143, 23500),
/*  29 */   GRADE4_SOLDIER(6, 645, 225, 42780),
/*  30 */   GRADE3_SOLDIER(7, 903, 356, 69700),
/*  31 */   GRADE2_SOLDIER(8, 1264, 561, 105600),
/*  32 */   GRADE1_SOLDIER(9, 1770, 885, 150800),
/*  33 */   STAR1_OFFICER(10, 2124, 1195, 214100, 1000),
/*  34 */   STAR2_OFFICER(11, 2549, 1616, 278700, 700),
/*  35 */   STAR3_OFFICER(12, 3059, 2184, 344500, 500),
/*  36 */   STAR4_OFFICER(13, 3671, 2949, 411700, 300),
/*  37 */   STAR5_OFFICER(14, 4405, 3981, 488200, 100),
/*  38 */   GENERAL(15, 5286, 5374, 565400, 30),
/*  39 */   GREAT_GENERAL(16, 6343, 7258, 643200, 10),
/*  40 */   COMMANDER(17, 7612, 9799, 721600, 3),
/*  41 */   SUPREME_COMMANDER(18, 9134, 13229, 800700, 1);
/*     */ 
/*     */   
/*     */   private int id;
/*     */ 
/*     */   
/*     */   private int pointsGained;
/*     */ 
/*     */   
/*     */   private int pointsLost;
/*     */   
/*     */   private int required;
/*     */   
/*     */   private int quota;
/*     */ 
/*     */   
/*     */   AbyssRankEnum(int id, int pointsGained, int pointsLost, int required) {
/*  58 */     this.id = id;
/*  59 */     this.pointsGained = pointsGained;
/*  60 */     this.pointsLost = pointsLost;
/*  61 */     this.required = required;
/*  62 */     this.quota = 0;
/*     */   }
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
/*     */   AbyssRankEnum(int id, int pointsGained, int pointsLost, int required, int quota) {
/*  76 */     this.id = id;
/*  77 */     this.pointsGained = pointsGained;
/*  78 */     this.pointsLost = pointsLost;
/*  79 */     this.required = required;
/*  80 */     this.quota = quota;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  88 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPointsLost() {
/*  96 */     return this.pointsLost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPointsGained() {
/* 104 */     return this.pointsGained;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequired() {
/* 112 */     return this.required;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQuota() {
/* 120 */     return this.quota;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AbyssRankEnum getRankById(int id) {
/* 129 */     for (AbyssRankEnum rank : values()) {
/*     */       
/* 131 */       if (rank.getId() == id)
/* 132 */         return rank; 
/*     */     } 
/* 134 */     throw new IllegalArgumentException("Invalid abyss rank provided");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AbyssRankEnum getRankForAp(int ap) {
/* 143 */     AbyssRankEnum r = GRADE9_SOLDIER; AbyssRankEnum[] arr$; int len$, i$;
/* 144 */     for (arr$ = values(), len$ = arr$.length, i$ = 0; i$ < len$; ) { AbyssRankEnum rank = arr$[i$];
/*     */       
/* 146 */       if (rank.getRequired() <= ap) {
/* 147 */         r = rank;
/*     */         i$++;
/*     */       }  }
/*     */     
/* 151 */     return r;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\AbyssRankEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */