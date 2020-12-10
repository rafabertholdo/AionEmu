/*     */ package com.aionemu.gameserver.model.account;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.legion.Legion;
/*     */ import com.aionemu.gameserver.model.legion.LegionMember;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.List;
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
/*     */ public class PlayerAccountData
/*     */ {
/*     */   private PlayerCommonData playerCommonData;
/*     */   private PlayerAppearance appereance;
/*     */   private List<Item> equipment;
/*     */   private Timestamp creationDate;
/*     */   private Timestamp deletionDate;
/*     */   private LegionMember legionMember;
/*     */   
/*     */   public PlayerAccountData(PlayerCommonData playerCommonData, PlayerAppearance appereance, List<Item> equipment, LegionMember legionMember) {
/*  49 */     this.playerCommonData = playerCommonData;
/*  50 */     this.appereance = appereance;
/*  51 */     this.equipment = equipment;
/*  52 */     this.legionMember = legionMember;
/*     */   }
/*     */ 
/*     */   
/*     */   public Timestamp getCreationDate() {
/*  57 */     return this.creationDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeletionDate(Timestamp deletionDate) {
/*  67 */     this.deletionDate = deletionDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getDeletionDate() {
/*  77 */     return this.deletionDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeletionTimeInSeconds() {
/*  87 */     return (this.deletionDate == null) ? 0 : (int)(this.deletionDate.getTime() / 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerCommonData getPlayerCommonData() {
/*  95 */     return this.playerCommonData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerCommonData(PlayerCommonData playerCommonData) {
/* 103 */     this.playerCommonData = playerCommonData;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerAppearance getAppereance() {
/* 108 */     return this.appereance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreationDate(Timestamp creationDate) {
/* 116 */     this.creationDate = creationDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Legion getLegion() {
/* 124 */     return this.legionMember.getLegion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLegionMember() {
/* 133 */     return (this.legionMember != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Item> getEquipment() {
/* 141 */     return this.equipment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEquipment(List<Item> equipment) {
/* 149 */     this.equipment = equipment;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\account\PlayerAccountData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */