/*     */ package com.aionemu.gameserver.network.aion;
/*     */ 
/*     */ import com.aionemu.commons.network.packet.BaseClientPacket;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class AionClientPacket
/*     */   extends BaseClientPacket<AionConnection>
/*     */   implements Cloneable
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(AionClientPacket.class);
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
/*     */   @Deprecated
/*     */   protected AionClientPacket(ByteBuffer buf, AionConnection client, int opcode) {
/*  50 */     super(buf, opcode);
/*  51 */     setConnection(client);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AionClientPacket(int opcode) {
/*  63 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void run() {
/*     */     try {
/*  74 */       runImpl();
/*     */     }
/*  76 */     catch (Throwable e) {
/*     */       
/*  78 */       String name = ((AionConnection)getConnection()).getAccount().getName();
/*  79 */       if (name == null) {
/*  80 */         name = ((AionConnection)getConnection()).getIP();
/*     */       }
/*  82 */       log.error("Error handling client (" + name + ") message :" + this, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendPacket(AionServerPacket msg) {
/*  94 */     ((AionConnection)getConnection()).sendPacket(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AionClientPacket clonePacket() {
/*     */     try {
/* 106 */       return (AionClientPacket)clone();
/*     */     }
/* 108 */     catch (CloneNotSupportedException e) {
/*     */       
/* 110 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\AionClientPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */