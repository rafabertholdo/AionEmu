/*     */ package com.aionemu.gameserver.model.items;
/*     */ 
/*     */ import javolution.util.FastList;
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
/*     */ public enum ItemSlot
/*     */ {
/*  28 */   MAIN_HAND(1),
/*  29 */   SUB_HAND(2),
/*  30 */   HELMET(4),
/*  31 */   TORSO(8),
/*  32 */   GLOVES(16),
/*  33 */   BOOTS(32),
/*  34 */   EARRINGS_LEFT(64),
/*  35 */   EARRINGS_RIGHT(128),
/*  36 */   RING_LEFT(256),
/*  37 */   RING_RIGHT(512),
/*  38 */   NECKLACE(1024),
/*  39 */   SHOULDER(2048),
/*  40 */   PANTS(4096),
/*  41 */   POWER_SHARD_RIGHT(8192),
/*  42 */   POWER_SHARD_LEFT(16384),
/*  43 */   WINGS(32768),
/*     */   
/*  45 */   WAIST(65536),
/*  46 */   MAIN_OFF_HAND(131072),
/*  47 */   SUB_OFF_HAND(262144),
/*     */ 
/*     */   
/*  50 */   MAIN_OR_SUB(MAIN_HAND.slotIdMask | SUB_HAND.slotIdMask, true),
/*  51 */   EARRING_RIGHT_OR_LEFT(EARRINGS_LEFT.slotIdMask | EARRINGS_RIGHT.slotIdMask, true),
/*  52 */   RING_RIGHT_OR_LEFT(RING_LEFT.slotIdMask | RING_RIGHT.slotIdMask, true),
/*  53 */   SHARD_RIGHT_OR_LEFT(POWER_SHARD_LEFT.slotIdMask | POWER_SHARD_RIGHT.slotIdMask, true),
/*  54 */   TORSO_GLOVE_FOOT_SHOULDER_LEG(0, true),
/*     */ 
/*     */   
/*  57 */   STIGMA1(524288),
/*  58 */   STIGMA2(1048576),
/*  59 */   STIGMA3(2097152),
/*  60 */   STIGMA4(4194304),
/*  61 */   STIGMA5(8388608),
/*  62 */   STIGMA6(16777216),
/*     */   
/*  64 */   NONE(33554432),
/*     */   
/*  66 */   ADV_STIGMA1(67108864),
/*  67 */   ADV_STIGMA2(134217728),
/*  68 */   ADV_STIGMA3(268435456),
/*  69 */   ADV_STIGMA4(536870912),
/*  70 */   ADV_STIGMA5(1073741824);
/*     */ 
/*     */ 
/*     */   
/*     */   private int slotIdMask;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean combo;
/*     */ 
/*     */ 
/*     */   
/*     */   ItemSlot(int mask, boolean combo) {
/*  83 */     this.slotIdMask = mask;
/*  84 */     this.combo = combo;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotIdMask() {
/*  89 */     return this.slotIdMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCombo() {
/*  97 */     return this.combo;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FastList<ItemSlot> getSlotsFor(int slotIdMask) {
/* 102 */     FastList<ItemSlot> slots = new FastList();
/* 103 */     for (ItemSlot itemSlot : values()) {
/*     */       
/* 105 */       int sumMask = itemSlot.slotIdMask & slotIdMask;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       if (sumMask > 0 && sumMask <= slotIdMask && !itemSlot.isCombo()) {
/* 111 */         slots.add(itemSlot);
/*     */       }
/*     */     } 
/* 114 */     if (slots.size() == 0) {
/* 115 */       throw new IllegalArgumentException("Invalid provided slotIdMask " + slotIdMask);
/*     */     }
/* 117 */     return slots;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\ItemSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */