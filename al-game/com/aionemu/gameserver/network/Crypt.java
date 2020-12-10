/*     */ package com.aionemu.gameserver.network;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Crypt
/*     */ {
/*     */   public static final byte staticClientPacketCode = 85;
/*     */   public static final byte staticServerPacketCode = 80;
/*  42 */   private static byte[] staticKey = "nKO/WctQ0AVLbpzfBkS6NevDYT8ourG5CRlmdjyJ72aswx4EPq1UgZhFMXH?3iI9".getBytes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] clientPacketKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] serverPacketKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEnabled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int enableKey() {
/*  66 */     if (this.clientPacketKey != null) {
/*  67 */       throw new KeyAlreadySetException();
/*     */     }
/*     */     
/*  70 */     int key = Rnd.nextInt();
/*     */     
/*  72 */     this.clientPacketKey = new byte[] { (byte)(key & 0xFF), (byte)(key >> 8 & 0xFF), (byte)(key >> 16 & 0xFF), (byte)(key >> 24 & 0xFF), -95, 108, 84, -121 };
/*     */ 
/*     */     
/*  75 */     this.serverPacketKey = new byte[this.clientPacketKey.length];
/*  76 */     System.arraycopy(this.clientPacketKey, 0, this.serverPacketKey, 0, this.clientPacketKey.length);
/*     */ 
/*     */     
/*  79 */     return (key ^ 0xCD92E451) + 1072876679;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean decrypt(ByteBuffer buf) {
/*  90 */     if (!this.isEnabled) {
/*  91 */       return false;
/*     */     }
/*  93 */     byte[] data = buf.array();
/*  94 */     int size = buf.remaining();
/*     */ 
/*     */     
/*  97 */     int arrayIndex = buf.arrayOffset() + buf.position();
/*     */ 
/*     */     
/* 100 */     int prev = data[arrayIndex];
/*     */ 
/*     */     
/* 103 */     data[arrayIndex++] = (byte)(data[arrayIndex++] ^ this.clientPacketKey[0] & 0xFF);
/*     */ 
/*     */     
/* 106 */     for (int i = 1; i < size; i++, arrayIndex++) {
/*     */       
/* 108 */       int curr = data[arrayIndex] & 0xFF;
/* 109 */       data[arrayIndex] = (byte)(data[arrayIndex] ^ staticKey[i & 0x3F] & 0xFF ^ this.clientPacketKey[i & 0x7] & 0xFF ^ prev);
/* 110 */       prev = curr;
/*     */     } 
/*     */ 
/*     */     
/* 114 */     long oldKey = (this.clientPacketKey[0] & 0xFFL) << 0L | (this.clientPacketKey[1] & 0xFFL) << 8L | (this.clientPacketKey[2] & 0xFFL) << 16L | (this.clientPacketKey[3] & 0xFFL) << 24L | (this.clientPacketKey[4] & 0xFFL) << 32L | (this.clientPacketKey[5] & 0xFFL) << 40L | (this.clientPacketKey[6] & 0xFFL) << 48L | (this.clientPacketKey[7] & 0xFFL) << 56L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     oldKey += size;
/*     */ 
/*     */     
/* 123 */     this.clientPacketKey[0] = (byte)(int)(oldKey >> 0L & 0xFFL);
/* 124 */     this.clientPacketKey[1] = (byte)(int)(oldKey >> 8L & 0xFFL);
/* 125 */     this.clientPacketKey[2] = (byte)(int)(oldKey >> 16L & 0xFFL);
/* 126 */     this.clientPacketKey[3] = (byte)(int)(oldKey >> 24L & 0xFFL);
/* 127 */     this.clientPacketKey[4] = (byte)(int)(oldKey >> 32L & 0xFFL);
/* 128 */     this.clientPacketKey[5] = (byte)(int)(oldKey >> 40L & 0xFFL);
/* 129 */     this.clientPacketKey[6] = (byte)(int)(oldKey >> 48L & 0xFFL);
/* 130 */     this.clientPacketKey[7] = (byte)(int)(oldKey >> 56L & 0xFFL);
/*     */     
/* 132 */     return validateClientPacket(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean validateClientPacket(ByteBuffer buf) {
/* 143 */     return (buf.get(0) == (buf.get(2) ^ 0xFFFFFFFF) && buf.get(1) == 85);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void encrypt(ByteBuffer buf) {
/* 153 */     if (!this.isEnabled) {
/*     */ 
/*     */       
/* 156 */       this.isEnabled = true;
/*     */       
/*     */       return;
/*     */     } 
/* 160 */     byte[] data = buf.array();
/* 161 */     int size = buf.remaining();
/*     */ 
/*     */     
/* 164 */     int arrayIndex = buf.arrayOffset() + buf.position();
/*     */ 
/*     */     
/* 167 */     data[arrayIndex] = (byte)(data[arrayIndex] ^ this.serverPacketKey[0] & 0xFF);
/*     */ 
/*     */     
/* 170 */     int prev = data[arrayIndex++];
/*     */ 
/*     */     
/* 173 */     for (int i = 1; i < size; i++, arrayIndex++) {
/*     */       
/* 175 */       data[arrayIndex] = (byte)(data[arrayIndex] ^ staticKey[i & 0x3F] & 0xFF ^ this.serverPacketKey[i & 0x7] & 0xFF ^ prev);
/* 176 */       prev = data[arrayIndex];
/*     */     } 
/*     */ 
/*     */     
/* 180 */     long oldKey = (this.serverPacketKey[0] & 0xFFL) << 0L | (this.serverPacketKey[1] & 0xFFL) << 8L | (this.serverPacketKey[2] & 0xFFL) << 16L | (this.serverPacketKey[3] & 0xFFL) << 24L | (this.serverPacketKey[4] & 0xFFL) << 32L | (this.serverPacketKey[5] & 0xFFL) << 40L | (this.serverPacketKey[6] & 0xFFL) << 48L | (this.serverPacketKey[7] & 0xFFL) << 56L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     oldKey += size;
/*     */ 
/*     */     
/* 189 */     this.serverPacketKey[0] = (byte)(int)(oldKey >> 0L & 0xFFL);
/* 190 */     this.serverPacketKey[1] = (byte)(int)(oldKey >> 8L & 0xFFL);
/* 191 */     this.serverPacketKey[2] = (byte)(int)(oldKey >> 16L & 0xFFL);
/* 192 */     this.serverPacketKey[3] = (byte)(int)(oldKey >> 24L & 0xFFL);
/* 193 */     this.serverPacketKey[4] = (byte)(int)(oldKey >> 32L & 0xFFL);
/* 194 */     this.serverPacketKey[5] = (byte)(int)(oldKey >> 40L & 0xFFL);
/* 195 */     this.serverPacketKey[6] = (byte)(int)(oldKey >> 48L & 0xFFL);
/* 196 */     this.serverPacketKey[7] = (byte)(int)(oldKey >> 56L & 0xFFL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte encodeOpcodec(int op) {
/* 207 */     return (byte)(op + 174 ^ 0xEE);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */