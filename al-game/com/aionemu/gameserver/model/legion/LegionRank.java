/*     */ package com.aionemu.gameserver.model.legion;
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
/*     */ public enum LegionRank
/*     */ {
/*  26 */   BRIGADE_GENERAL(0),
/*  27 */   CENTURION(1),
/*  28 */   LEGIONARY(2);
/*     */   
/*     */   private static final int LP_LEGION_WAREHOUSE = 4;
/*     */   
/*     */   private static final int LP_INVITE_TO_LEGION = 8;
/*     */   
/*     */   private static final int LP_KICK_FROM_LEGION = 16;
/*     */   
/*     */   private static final int LP_COMBINATION_60_12 = 12;
/*     */   
/*     */   private static final int LP_COMBINATION_60_13 = 20;
/*     */   
/*     */   private static final int LP_COMBINATION_60_23 = 24;
/*     */   
/*     */   private static final int LP_COMBINATION_60_123 = 28;
/*     */   private static final int LP_EDIT_ANNOUNCEMENT = 2;
/*     */   private static final int LP_ARTIFACT = 4;
/*     */   private static final int LP_GATE_GUARDIAN_STONE = 8;
/*     */   private static final int LP_COMBINATION_00_12 = 6;
/*     */   private static final int LP_COMBINATION_00_13 = 10;
/*     */   private static final int LP_COMBINATION_00_23 = 12;
/*     */   private static final int LP_COMBINATION_00_123 = 14;
/*     */   private byte rank;
/*     */   
/*     */   LegionRank(int rank) {
/*  53 */     this.rank = (byte)rank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getRankId() {
/*  63 */     return this.rank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseGateGuardianStone(int centurionPermission2, int legionarPermission2) {
/*  71 */     switch (this) {
/*     */ 
/*     */       
/*     */       case CENTURION:
/*  75 */         if (centurionPermission2 == 8 || centurionPermission2 == 10 || centurionPermission2 == 12 || centurionPermission2 == 14)
/*     */         {
/*     */           
/*  78 */           return true;
/*     */         }
/*     */         break;
/*     */       case LEGIONARY:
/*  82 */         if (legionarPermission2 == 8)
/*  83 */           return true; 
/*     */         break;
/*     */     } 
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseArtifact(int centurionPermission2) {
/*  94 */     switch (this) {
/*     */ 
/*     */ 
/*     */       
/*     */       case CENTURION:
/*  99 */         if (centurionPermission2 == 4 || centurionPermission2 == 6 || centurionPermission2 == 12 || centurionPermission2 == 14)
/*     */         {
/*     */           
/* 102 */           return true;
/*     */         }
/*     */         break;
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canEditAnnouncement(int centurionPermission2) {
/* 114 */     switch (this) {
/*     */ 
/*     */ 
/*     */       
/*     */       case CENTURION:
/* 119 */         if (centurionPermission2 == 2 || centurionPermission2 == 10 || centurionPermission2 == 12 || centurionPermission2 == 14)
/*     */         {
/*     */           
/* 122 */           return true;
/*     */         }
/*     */         break;
/*     */     } 
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseLegionWarehouse(int centurionPermission1) {
/* 134 */     switch (this) {
/*     */ 
/*     */ 
/*     */       
/*     */       case CENTURION:
/* 139 */         if (centurionPermission1 == 4 || centurionPermission1 == 20 || centurionPermission1 == 20 || centurionPermission1 == 28)
/*     */         {
/*     */           
/* 142 */           return true;
/*     */         }
/*     */         break;
/*     */     } 
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canKickFromLegion(int centurionPermission1) {
/* 154 */     switch (this) {
/*     */ 
/*     */ 
/*     */       
/*     */       case CENTURION:
/* 159 */         if (centurionPermission1 == 16 || centurionPermission1 == 12 || centurionPermission1 == 24 || centurionPermission1 == 28)
/*     */         {
/*     */           
/* 162 */           return true;
/*     */         }
/*     */         break;
/*     */     } 
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInviteToLegion(int centurionPermission1) {
/* 174 */     switch (this) {
/*     */ 
/*     */ 
/*     */       
/*     */       case CENTURION:
/* 179 */         if (centurionPermission1 == 8 || centurionPermission1 == 20 || centurionPermission1 == 24 || centurionPermission1 == 28)
/*     */         {
/*     */           
/* 182 */           return true;
/*     */         }
/*     */         break;
/*     */     } 
/* 186 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionRank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */