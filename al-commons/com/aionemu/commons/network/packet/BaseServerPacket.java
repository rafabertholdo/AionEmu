/*     */ package com.aionemu.commons.network.packet;
/*     */ 
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
/*     */ public abstract class BaseServerPacket
/*     */   extends BasePacket
/*     */ {
/*     */   protected BaseServerPacket(int opcode) {
/*  36 */     super(BasePacket.PacketType.SERVER, opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseServerPacket() {
/*  45 */     super(BasePacket.PacketType.SERVER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeD(ByteBuffer buf, int value) {
/*  56 */     buf.putInt(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeH(ByteBuffer buf, int value) {
/*  67 */     buf.putShort((short)value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeC(ByteBuffer buf, int value) {
/*  78 */     buf.put((byte)value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeDF(ByteBuffer buf, double value) {
/*  89 */     buf.putDouble(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeF(ByteBuffer buf, float value) {
/* 100 */     buf.putFloat(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeQ(ByteBuffer buf, long value) {
/* 111 */     buf.putLong(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeS(ByteBuffer buf, String text) {
/* 122 */     if (text == null) {
/*     */       
/* 124 */       buf.putChar(false);
/*     */     }
/*     */     else {
/*     */       
/* 128 */       int len = text.length();
/* 129 */       for (int i = 0; i < len; i++)
/* 130 */         buf.putChar(text.charAt(i)); 
/* 131 */       buf.putChar(false);
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
/*     */   protected final void writeB(ByteBuffer buf, byte[] data) {
/* 143 */     buf.put(data);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\packet\BaseServerPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */