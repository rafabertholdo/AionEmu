/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class SM_CRAFT_UPDATE
/*     */   extends AionServerPacket
/*     */ {
/*     */   private int skillId;
/*     */   private int itemId;
/*     */   private int action;
/*     */   private int success;
/*     */   private int failure;
/*     */   private int nameId;
/*     */   
/*     */   public SM_CRAFT_UPDATE(int skillId, ItemTemplate item, int success, int failure, int action) {
/*  47 */     this.action = action;
/*  48 */     this.skillId = skillId;
/*  49 */     this.itemId = item.getTemplateId();
/*  50 */     this.success = success;
/*  51 */     this.failure = failure;
/*  52 */     this.nameId = item.getNameId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  58 */     writeH(buf, this.skillId);
/*  59 */     writeC(buf, this.action);
/*  60 */     writeD(buf, this.itemId);
/*     */     
/*  62 */     switch (this.action) {
/*     */ 
/*     */       
/*     */       case 0:
/*  66 */         writeD(buf, this.success);
/*  67 */         writeD(buf, this.failure);
/*  68 */         writeD(buf, 0);
/*  69 */         writeD(buf, 1200);
/*  70 */         writeD(buf, 1330048);
/*  71 */         writeH(buf, 36);
/*  72 */         writeD(buf, this.nameId);
/*  73 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/*  78 */         writeD(buf, this.success);
/*  79 */         writeD(buf, this.failure);
/*  80 */         writeD(buf, 700);
/*  81 */         writeD(buf, 1200);
/*  82 */         writeD(buf, 0);
/*  83 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*  88 */         writeD(buf, this.success);
/*  89 */         writeD(buf, this.failure);
/*  90 */         writeD(buf, 700);
/*  91 */         writeD(buf, 1200);
/*  92 */         writeD(buf, 0);
/*  93 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/*  98 */         writeD(buf, this.success);
/*  99 */         writeD(buf, this.failure);
/* 100 */         writeD(buf, 700);
/* 101 */         writeD(buf, 1200);
/* 102 */         writeD(buf, 0);
/* 103 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 108 */         writeD(buf, this.success);
/* 109 */         writeD(buf, this.failure);
/* 110 */         writeD(buf, 700);
/* 111 */         writeD(buf, 1200);
/* 112 */         writeD(buf, 0);
/* 113 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 118 */         writeD(buf, this.success);
/* 119 */         writeD(buf, this.failure);
/* 120 */         writeD(buf, 0);
/* 121 */         writeD(buf, 1200);
/* 122 */         writeD(buf, 1330050);
/* 123 */         writeH(buf, 36);
/* 124 */         writeD(buf, this.nameId);
/* 125 */         writeH(buf, 0);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CRAFT_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */