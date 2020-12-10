/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.group.LootDistribution;
/*     */ import com.aionemu.gameserver.model.group.LootGroupRules;
/*     */ import com.aionemu.gameserver.model.group.LootRuleType;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
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
/*     */ public class CM_DISTRIBUTION_SETTINGS
/*     */   extends AionClientPacket
/*     */ {
/*     */   private LootRuleType lootrules;
/*     */   private LootDistribution autodistribution;
/*     */   private int common_item_above;
/*     */   private int superior_item_above;
/*     */   private int heroic_item_above;
/*     */   private int fabled_item_above;
/*     */   private int ethernal_item_above;
/*     */   private int over_ethernal;
/*     */   private int over_over_ethernal;
/*     */   
/*     */   public CM_DISTRIBUTION_SETTINGS(int opcode) {
/*  47 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  56 */     switch (readD()) {
/*     */       
/*     */       case 0:
/*  59 */         this.lootrules = LootRuleType.FREEFORALL;
/*     */         break;
/*     */       case 1:
/*  62 */         this.lootrules = LootRuleType.ROUNDROBIN;
/*     */         break;
/*     */       case 2:
/*  65 */         this.lootrules = LootRuleType.LEADER;
/*     */         break;
/*     */       default:
/*  68 */         this.lootrules = LootRuleType.FREEFORALL;
/*     */         break;
/*     */     } 
/*     */     
/*  72 */     switch (readD()) {
/*     */       
/*     */       case 0:
/*  75 */         this.autodistribution = LootDistribution.NORMAL;
/*     */         break;
/*     */       case 2:
/*  78 */         this.autodistribution = LootDistribution.ROLL_DICE;
/*     */         break;
/*     */       case 3:
/*  81 */         this.autodistribution = LootDistribution.BID;
/*     */         break;
/*     */       default:
/*  84 */         this.autodistribution = LootDistribution.NORMAL;
/*     */         break;
/*     */     } 
/*     */     
/*  88 */     this.common_item_above = readD();
/*  89 */     this.superior_item_above = readD();
/*  90 */     this.heroic_item_above = readD();
/*  91 */     this.fabled_item_above = readD();
/*  92 */     this.ethernal_item_above = readD();
/*  93 */     this.over_ethernal = readD();
/*  94 */     this.over_over_ethernal = readD();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/* 103 */     Player leader = ((AionConnection)getConnection()).getActivePlayer();
/* 104 */     if (leader != null) {
/*     */       
/* 106 */       PlayerGroup pg = leader.getPlayerGroup();
/* 107 */       if (pg != null)
/* 108 */         pg.setLootGroupRules(new LootGroupRules(this.lootrules, this.autodistribution, this.common_item_above, this.superior_item_above, this.heroic_item_above, this.fabled_item_above, this.ethernal_item_above, this.over_ethernal, this.over_over_ethernal)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_DISTRIBUTION_SETTINGS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */