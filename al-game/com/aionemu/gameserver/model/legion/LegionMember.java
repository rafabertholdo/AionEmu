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
/*     */ public class LegionMember
/*     */ {
/*     */   private static final int LP_CENT_NONE = 96;
/*  26 */   private int objectId = 0;
/*  27 */   protected Legion legion = null;
/*  28 */   protected String nickname = "";
/*  29 */   protected String selfIntro = "";
/*     */   
/*  31 */   protected LegionRank rank = LegionRank.LEGIONARY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionMember(int objectId) {
/*  38 */     this.objectId = objectId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionMember(int objectId, Legion legion, LegionRank rank) {
/*  46 */     setObjectId(objectId);
/*  47 */     setLegion(legion);
/*  48 */     setRank(rank);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionMember() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegion(Legion legion) {
/*  64 */     this.legion = legion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Legion getLegion() {
/*  72 */     return this.legion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRank(LegionRank rank) {
/*  81 */     this.rank = rank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegionRank getRank() {
/*  89 */     return this.rank;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBrigadeGeneral() {
/*  94 */     return (this.rank == LegionRank.BRIGADE_GENERAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNickname(String nickname) {
/* 103 */     this.nickname = nickname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNickname() {
/* 111 */     return this.nickname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelfIntro(String selfIntro) {
/* 120 */     this.selfIntro = selfIntro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelfIntro() {
/* 128 */     return this.selfIntro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectId(int objectId) {
/* 137 */     this.objectId = objectId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getObjectId() {
/* 145 */     return this.objectId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRights(int type) {
/* 150 */     if (getRank() == LegionRank.BRIGADE_GENERAL) {
/* 151 */       return true;
/*     */     }
/* 153 */     int legionarPermission2 = getLegion().getLegionarPermission2();
/* 154 */     int centurionPermission1 = getLegion().getCenturionPermission1() - 96;
/* 155 */     int centurionPermission2 = getLegion().getCenturionPermission2();
/*     */     
/* 157 */     switch (type) {
/*     */       
/*     */       case 1:
/* 160 */         if (getRank().canInviteToLegion(centurionPermission1)) {
/* 161 */           return true;
/*     */         }
/*     */       case 2:
/* 164 */         if (getRank().canKickFromLegion(centurionPermission1)) {
/* 165 */           return true;
/*     */         }
/*     */       case 3:
/* 168 */         if (getRank().canUseLegionWarehouse(centurionPermission1)) {
/* 169 */           return true;
/*     */         }
/*     */       case 4:
/* 172 */         if (getRank().canEditAnnouncement(centurionPermission2)) {
/* 173 */           return true;
/*     */         }
/*     */       case 5:
/* 176 */         if (getRank().canUseArtifact(centurionPermission2)) {
/* 177 */           return true;
/*     */         }
/*     */       case 6:
/* 180 */         if (getRank().canUseGateGuardianStone(centurionPermission2, legionarPermission2))
/* 181 */           return true;  break;
/*     */     } 
/* 183 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionMember.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */