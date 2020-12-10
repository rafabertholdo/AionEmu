/*     */ package com.aionemu.gameserver.network.aion;
/*     */ 
/*     */ import com.aionemu.commons.network.packet.BaseServerPacket;
/*     */ import com.aionemu.gameserver.network.Crypt;
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
/*     */ 
/*     */ public abstract class AionServerPacket
/*     */   extends BaseServerPacket
/*     */ {
/*     */   private ByteBuffer buf;
/*     */   
/*     */   protected AionServerPacket() {
/*  42 */     setOpcode(ServerPacketsOpcodes.getOpcode((Class)getClass()));
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
/*     */   private final void writeOP(ByteBuffer buf, int value) {
/*  54 */     byte op = Crypt.encodeOpcodec(value);
/*  55 */     buf.put(op);
/*     */ 
/*     */     
/*  58 */     buf.put((byte)80);
/*     */ 
/*     */     
/*  61 */     buf.put((byte)(op ^ 0xFFFFFFFF));
/*     */   }
/*     */ 
/*     */   
/*     */   public final void write(AionConnection con) {
/*  66 */     write(con, this.buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write(AionConnection con, ByteBuffer buf) {
/*  76 */     buf.putShort((short)0);
/*  77 */     writeOP(buf, getOpcode());
/*  78 */     writeImpl(con, buf);
/*  79 */     buf.flip();
/*  80 */     buf.putShort((short)buf.limit());
/*  81 */     ByteBuffer b = buf.slice();
/*  82 */     buf.position(0);
/*  83 */     con.encrypt(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getBuf() {
/* 102 */     return this.buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBuf(ByteBuffer buf) {
/* 110 */     this.buf = buf;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\AionServerPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */