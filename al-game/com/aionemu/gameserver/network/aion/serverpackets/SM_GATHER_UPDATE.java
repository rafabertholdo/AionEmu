/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.GatherableTemplate;
/*     */ import com.aionemu.gameserver.model.templates.gather.Material;
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
/*     */ public class SM_GATHER_UPDATE
/*     */   extends AionServerPacket
/*     */ {
/*     */   private GatherableTemplate template;
/*     */   private int action;
/*     */   private int itemId;
/*     */   private int success;
/*     */   private int failure;
/*     */   private int nameId;
/*     */   
/*     */   public SM_GATHER_UPDATE(GatherableTemplate template, Material material, int success, int failure, int action) {
/*  41 */     this.action = action;
/*  42 */     this.template = template;
/*  43 */     this.itemId = material.getItemid().intValue();
/*  44 */     this.success = success;
/*  45 */     this.failure = failure;
/*  46 */     this.nameId = material.getNameid().intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  52 */     writeH(buf, this.template.getSkillLevel());
/*  53 */     writeC(buf, this.action);
/*  54 */     writeD(buf, this.itemId);
/*     */     
/*  56 */     switch (this.action) {
/*     */ 
/*     */       
/*     */       case 0:
/*  60 */         writeD(buf, this.template.getSuccessAdj());
/*  61 */         writeD(buf, this.template.getFailureAdj());
/*  62 */         writeD(buf, 0);
/*  63 */         writeD(buf, 1200);
/*  64 */         writeD(buf, 1330011);
/*  65 */         writeH(buf, 36);
/*  66 */         writeD(buf, this.nameId);
/*  67 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/*  72 */         writeD(buf, this.success);
/*  73 */         writeD(buf, this.failure);
/*  74 */         writeD(buf, 700);
/*  75 */         writeD(buf, 1200);
/*  76 */         writeD(buf, 0);
/*  77 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*  82 */         writeD(buf, this.template.getSuccessAdj());
/*  83 */         writeD(buf, this.failure);
/*  84 */         writeD(buf, 700);
/*  85 */         writeD(buf, 1200);
/*  86 */         writeD(buf, 0);
/*  87 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/*  92 */         writeD(buf, 0);
/*  93 */         writeD(buf, 0);
/*  94 */         writeD(buf, 700);
/*  95 */         writeD(buf, 1200);
/*  96 */         writeD(buf, 1330080);
/*  97 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 102 */         writeD(buf, this.template.getSuccessAdj());
/* 103 */         writeD(buf, this.failure);
/* 104 */         writeD(buf, 700);
/* 105 */         writeD(buf, 1200);
/* 106 */         writeD(buf, 0);
/* 107 */         writeH(buf, 0);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 112 */         writeD(buf, this.success);
/* 113 */         writeD(buf, this.template.getFailureAdj());
/* 114 */         writeD(buf, 0);
/* 115 */         writeD(buf, 1200);
/* 116 */         writeD(buf, 1330079);
/* 117 */         writeH(buf, 36);
/* 118 */         writeD(buf, this.nameId);
/* 119 */         writeH(buf, 0);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_GATHER_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */