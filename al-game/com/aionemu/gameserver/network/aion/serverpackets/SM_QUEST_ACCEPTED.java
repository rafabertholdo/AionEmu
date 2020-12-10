/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
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
/*     */ public class SM_QUEST_ACCEPTED
/*     */   extends AionServerPacket
/*     */ {
/*     */   private int questId;
/*     */   private int status;
/*     */   private int step;
/*     */   private int action;
/*     */   private int timer;
/*     */   
/*     */   public SM_QUEST_ACCEPTED(int questId, int status, int step) {
/*  44 */     this.action = 1;
/*  45 */     this.questId = questId;
/*  46 */     this.status = status;
/*  47 */     this.step = step;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_QUEST_ACCEPTED(int questId, QuestStatus status, int step) {
/*  55 */     this.action = 2;
/*  56 */     this.questId = questId;
/*  57 */     this.status = status.value();
/*  58 */     this.step = step;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_QUEST_ACCEPTED(int questId) {
/*  66 */     this.action = 3;
/*  67 */     this.questId = questId;
/*  68 */     this.status = 0;
/*  69 */     this.step = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_QUEST_ACCEPTED(int questId, int timer) {
/*  77 */     this.action = 4;
/*  78 */     this.questId = questId;
/*  79 */     this.timer = timer;
/*  80 */     this.step = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  88 */     switch (this.action) {
/*     */       
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*  93 */         writeC(buf, this.action);
/*  94 */         writeD(buf, this.questId);
/*  95 */         writeC(buf, this.status);
/*  96 */         writeC(buf, 0);
/*  97 */         writeD(buf, this.step);
/*  98 */         writeH(buf, 0);
/*     */         break;
/*     */       case 4:
/* 101 */         writeC(buf, this.action);
/* 102 */         writeD(buf, this.questId);
/* 103 */         writeD(buf, this.timer);
/* 104 */         writeC(buf, 1);
/* 105 */         writeH(buf, 0);
/* 106 */         writeC(buf, 1);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_QUEST_ACCEPTED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */