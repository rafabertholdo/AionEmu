/*     */ package com.aionemu.gameserver.model;
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
/*     */ public class Petition
/*     */ {
/*     */   private final int petitionId;
/*     */   private final int playerObjId;
/*     */   private final PetitionType type;
/*     */   private final String title;
/*     */   private final String contentText;
/*     */   private final String additionalData;
/*     */   private final PetitionStatus status;
/*     */   
/*     */   public Petition(int petitionId) {
/*  35 */     this.petitionId = petitionId;
/*  36 */     this.playerObjId = 0;
/*  37 */     this.type = PetitionType.INQUIRY;
/*  38 */     this.title = "";
/*  39 */     this.contentText = "";
/*  40 */     this.additionalData = "";
/*  41 */     this.status = PetitionStatus.PENDING;
/*     */   }
/*     */ 
/*     */   
/*     */   public Petition(int petitionId, int playerObjId, int petitionTypeId, String title, String contentText, String additionalData, int petitionStatus) {
/*  46 */     this.petitionId = petitionId;
/*  47 */     this.playerObjId = playerObjId;
/*  48 */     switch (petitionTypeId) {
/*     */       case 256:
/*  50 */         this.type = PetitionType.CHARACTER_STUCK; break;
/*  51 */       case 512: this.type = PetitionType.CHARACTER_RESTORATION; break;
/*  52 */       case 768: this.type = PetitionType.BUG; break;
/*  53 */       case 1024: this.type = PetitionType.QUEST; break;
/*  54 */       case 1280: this.type = PetitionType.UNACCEPTABLE_BEHAVIOR; break;
/*  55 */       case 1536: this.type = PetitionType.SUGGESTION; break;
/*  56 */       case 65280: this.type = PetitionType.INQUIRY; break;
/*  57 */       default: this.type = PetitionType.INQUIRY; break;
/*     */     } 
/*  59 */     this.title = title;
/*  60 */     this.contentText = contentText;
/*  61 */     this.additionalData = additionalData;
/*  62 */     switch (petitionStatus) {
/*     */       case 0:
/*  64 */         this.status = PetitionStatus.PENDING; return;
/*  65 */       case 1: this.status = PetitionStatus.IN_PROGRESS; return;
/*  66 */       case 2: this.status = PetitionStatus.REPLIED; return;
/*  67 */     }  this.status = PetitionStatus.PENDING;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPlayerObjId() {
/*  73 */     return this.playerObjId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPetitionId() {
/*  78 */     return this.petitionId;
/*     */   }
/*     */ 
/*     */   
/*     */   public PetitionType getPetitionType() {
/*  83 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  88 */     return this.title;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentText() {
/*  93 */     return this.contentText;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAdditionalData() {
/*  98 */     return this.additionalData;
/*     */   }
/*     */ 
/*     */   
/*     */   public PetitionStatus getStatus() {
/* 103 */     return this.status;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\Petition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */