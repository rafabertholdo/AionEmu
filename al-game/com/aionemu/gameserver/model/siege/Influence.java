/*     */ package com.aionemu.gameserver.model.siege;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_INFLUENCE_RATIO;
/*     */ import com.aionemu.gameserver.services.SiegeService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
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
/*     */ public class Influence
/*     */ {
/*  33 */   private float elyos = 0.0F;
/*  34 */   private float asmos = 0.0F;
/*  35 */   private float balaur = 0.0F;
/*     */ 
/*     */   
/*     */   private Influence() {
/*  39 */     calculateInfluence();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Influence getInstance() {
/*  44 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recalculateInfluence() {
/*  52 */     calculateInfluence();
/*     */     
/*  54 */     broadcastInfluencePacket();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateInfluence() {
/*  62 */     int total = 0;
/*  63 */     int asmos = 0;
/*  64 */     int elyos = 0;
/*  65 */     int balaur = 0;
/*     */     
/*  67 */     for (SiegeLocation sLoc : SiegeService.getInstance().getSiegeLocations().values()) {
/*     */ 
/*     */       
/*  70 */       total += sLoc.getInfluenceValue();
/*  71 */       switch (sLoc.getRace()) {
/*     */         
/*     */         case BALAUR:
/*  74 */           balaur += sLoc.getInfluenceValue();
/*     */         
/*     */         case ASMODIANS:
/*  77 */           asmos += sLoc.getInfluenceValue();
/*     */         
/*     */         case ELYOS:
/*  80 */           elyos += sLoc.getInfluenceValue();
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/*  85 */     this.balaur = balaur / total;
/*  86 */     this.elyos = elyos / total;
/*  87 */     this.asmos = asmos / total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void broadcastInfluencePacket() {
/*  96 */     SM_INFLUENCE_RATIO pkt = new SM_INFLUENCE_RATIO();
/*     */     
/*  98 */     for (Player player : World.getInstance().getAllPlayers())
/*     */     {
/* 100 */       PacketSendUtility.sendPacket(player, (AionServerPacket)pkt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getElyos() {
/* 109 */     return this.elyos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAsmos() {
/* 117 */     return this.asmos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBalaur() {
/* 125 */     return this.balaur;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 131 */     protected static final Influence instance = new Influence();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\Influence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */